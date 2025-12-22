package com.example.board.repository;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.member.constant.MemberRole;
import com.example.board.member.entity.Member;
import com.example.board.member.repository.MemberRepository;
import com.example.board.post.dto.PageRequestDTO;
import com.example.board.post.entity.Board;
import com.example.board.post.repository.BoardRepository;
import com.example.board.reply.entity.Reply;
import com.example.board.reply.repository.ReplyRepository;

@Disabled
@SpringBootTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // delete test
    @Test
    @Transactional
    @Commit
    public void deleteByBnoTest() {
        replyRepository.deleteByBno(1L);
        boardRepository.deleteById(1L);
    }

    @Test
    public void insertMemberTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .password(passwordEncoder.encode("1111"))
                    .fromSocial(false)
                    .name("user" + i)
                    .build();
            member.addMemberRole(MemberRole.USER);

            if (i > 9) {
                member.addMemberRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });
    }

    @Test
    public void insertBoardTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            int idx = (int) (Math.random() * 10) + 1;
            Member member = Member.builder()
                    .email("user" + idx + "@gmail.com")
                    .build();
            Board board = Board.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        });
    }

    @Test
    public void insertReplyTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            long idx = (long) (Math.random() * 100) + 1;
            int midx = (int) (Math.random() * 10) + 1;
            Board board = Board.builder()
                    .bno(idx)
                    .build();
            Member member = Member.builder()
                    .email("user" + midx + "@gmail.com")
                    .build();
            Reply reply = Reply.builder()
                    .text("reply..." + i)
                    .replyer(member)
                    .board(board)
                    .build();
            replyRepository.save(reply);
        });
    }

    @Test
    public void insertReplyTest2() {
        Board board = Board.builder().bno(500L).build();
        IntStream.rangeClosed(1, 15).forEach(i -> {

            Reply reply = Reply.builder()
                    .text("reply..." + i)
                    // .replyer("guest" + i)
                    .board(board)
                    .build();
            replyRepository.save(reply);
        });
    }

    // board 읽기
    @Test
    @Transactional(readOnly = true)
    public void readBoardTest() {
        List<Board> list = boardRepository.findAll();
        list.forEach(board -> {
            System.out.println(board);
        });

    }

    // querydsl 테스트
    @Test
    public void listTest() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(0)
                .size(20)
                .type("tcw")
                .keyword("title")
                .build();

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage(),
                pageRequestDTO.getSize(),
                Sort.by("bno").descending());

        // Pageable pageable = PageRequest.of(pageRequestDTO.getPage(),
        // pageRequestDTO.getSize()); // sort 기준이 하나일 때

        Page<Object[]> result = boardRepository.list(pageRequestDTO.getType(), pageRequestDTO.getKeyword(), pageable);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void getBoardWithWriterListTest() {
        List<Object[]> result = boardRepository.getBoardWithWriterList();
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }

    }

    @Test
    @Transactional(readOnly = true)
    public void getBoardWithWriterTest() {
        // JPA
        Board board = boardRepository.findById(33L).get();
        System.out.println(board);
        // 댓글 가져오기
        System.out.println(board.getReplies());
    }

    @Test
    @Transactional(readOnly = true)
    public void getBoardWithWriterTest2() {
        // JPQL(@Query)
        List<Object[]> result = boardRepository.getBoardWithReply(33L);
        // for (Object[] objects : result) { // 1
        // System.out.println(Arrays.toString(objects));
        // }
        result.forEach(obj -> System.out.println(Arrays.toString(obj))); // 2
    }

    // @Test
    // public void getBoardWithReplyCountTest() {
    // Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
    // Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
    // // for (Object[] objects : result) { // 1
    // // System.out.println(Arrays.toString(objects));
    // // Board board = (Board) objects[0];
    // // Member member = (Member) objects[1];
    // // Long replyCnt = (Long) objects[2];
    // // System.out.println(board);
    // // System.out.println(member);
    // // System.out.println(replyCnt);
    // // }

    // // Stream<Object[]> data = result.get();
    // // Stream<Object[]> data2 = result.getContent().stream();

    // result.get().forEach(objects -> {
    // // System.out.println(Arrays.toString(obj));
    // Board board = (Board) objects[0];
    // Member member = (Member) objects[1];
    // Long replyCnt = (Long) objects[2];

    // }); // 2
    // Function<Object[], String> f = Arrays::toString;
    // // Object[] objects
    // result.get().forEach(objects -> System.out.println(f.apply(objects))); //
    // [Ljava.lang.Object;@5df785d1
    // }

    @Test
    public void getBoardByBnoTest() {
        Object result = boardRepository.getBoardByBno(500L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }
}
