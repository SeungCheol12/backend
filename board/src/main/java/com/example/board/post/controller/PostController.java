package com.example.board.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.post.dto.BoardDTO;
import com.example.board.post.dto.PageRequestDTO;
import com.example.board.post.dto.PageResultDTO;
import com.example.board.post.service.BoardService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor // final
@Controller
@Log4j2
@RequestMapping("/board")
public class PostController {
    private final BoardService boardService;

    @PostMapping("/remove")
    public String postDelete(PageRequestDTO requesttDTO, BoardDTO dto, RedirectAttributes rttr) {
        log.info("삭제 {} {}", dto, requesttDTO);
        boardService.delete(dto);
        rttr.addFlashAttribute("msg", "게시글 삭제가 완료되었습니다.");
        // rttr.addAttribute("bno", dto.getBno()); // => list로 redirect 되어서 생략 가능하다
        rttr.addAttribute("size", requesttDTO.getSize());
        rttr.addAttribute("page", requesttDTO.getPage());
        return "redirect:/board/list";
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(@RequestParam("bno") Long bno, Model model, PageRequestDTO requesttDTO) {
        log.info("get or modify {}", bno);

        BoardDTO dto = boardService.getRow(bno);
        model.addAttribute("dto", dto);
    }

    // ?page=1&size=10
    @GetMapping("/list")
    public void getList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list 요청 {}", pageRequestDTO);

        PageResultDTO<BoardDTO> result = boardService.getList(pageRequestDTO);
        model.addAttribute("result", result);
    }

    @PostMapping("/modify")
    public String postModify(PageRequestDTO requesttDTO, BoardDTO dto, RedirectAttributes rttr) {
        log.info("수정 {} {}", dto, requesttDTO);
        boardService.update(dto);
        rttr.addFlashAttribute("msg", "게시글 수정이 완료되었습니다.");
        rttr.addAttribute("bno", dto.getBno());
        rttr.addAttribute("size", requesttDTO.getSize());
        rttr.addAttribute("page", requesttDTO.getPage());
        return "redirect:/board/read";
    }

    // 유효성 검증 + 데이터 도착 +
    @GetMapping("/create")
    public void getCreate(BoardDTO dto) {
        log.info("작성 폼 요청");
    }

    @PostMapping("/create")
    public String postCreate(@Valid BoardDTO dto, BindingResult result, RedirectAttributes rttr) {
        log.info("작성 {}", dto);

        if (result.hasErrors()) {
            return "/board/create";
        }
        // 서비스 호출
        Long bno = boardService.insert(dto);
        rttr.addFlashAttribute("msg", bno + "번 게시물이 등록되었습니다");
        return "redirect:/board/list";
    }

}
