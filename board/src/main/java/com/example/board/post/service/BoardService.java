package com.example.board.post.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.member.entity.Member;
import com.example.board.post.dto.BoardDTO;
import com.example.board.post.dto.PageRequestDTO;
import com.example.board.post.dto.PageResultDTO;
import com.example.board.post.entity.Board;
import com.example.board.post.repository.BoardRepository;
import com.example.board.reply.entity.Reply;
import com.example.board.reply.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Transactional
@Log4j2
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    // crud
    @Transactional(readOnly = true)
    public PageResultDTO<BoardDTO> getList(PageRequestDTO requesttDTO) {
        Pageable pageable = PageRequest.of(requesttDTO.getPage() - 1, requesttDTO.getSize(),
                Sort.by("bno").descending());

        // Query 사용
        // Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        Page<Object[]> result = boardRepository.list(requesttDTO.getType(), requesttDTO.getKeyword(), pageable);

        // 번호, 제목(댓글 개수), 작성자, 작성일
        Function<Object[], BoardDTO> f = en -> entityToDto((Board) en[0], (Member) en[1], (Long) en[2]);

        List<BoardDTO> dtoList = result.stream().map(f).collect(Collectors.toList());
        long totalCount = result.getTotalElements();

        PageResultDTO<BoardDTO> pageResultDTO = PageResultDTO.<BoardDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(requesttDTO)
                .totalCount(totalCount).build();
        return pageResultDTO;
    }

    @Transactional(readOnly = true)
    public BoardDTO getRow(Long bno) {
        Object result = boardRepository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        BoardDTO dto = entityToDto((Board) arr[0], (Member) arr[1], (Long) arr[2]);

        return dto;
    }

    // 게시글 등록
    public Long insert(BoardDTO dto) {
        Member member = Member.builder()
                .email(dto.getWriterEmail())
                .build();
        Board board = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return boardRepository.save(board).getBno();
    }

    public void update(BoardDTO dto) {
        Board board = boardRepository.findById(dto.getBno()).get();
        board.changeTitle(dto.getTitle());
        board.changeContent(dto.getContent());
        // boardRepository.save(null);
    }

    public void delete(BoardDTO dto) {
        // 게시글 삭제
        // 자식으로 댓글이 존재
        replyRepository.deleteByBno(dto.getBno());
        boardRepository.deleteById(dto.getBno());
    }

    // entity => dto
    private BoardDTO entityToDto(Board board, Member member, Long replyCnt) {
        BoardDTO dto = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .createDateTime(board.getCreateDateTime())
                .updatedDateTime(board.getUpdatedDateTime())
                .replyCnt(replyCnt != null ? replyCnt.intValue() : 0)
                .build();

        return dto;
    }
}
