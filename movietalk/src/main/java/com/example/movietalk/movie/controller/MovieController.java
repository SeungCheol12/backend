package com.example.movietalk.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.movietalk.movie.dto.MovieDTO;
import com.example.movietalk.movie.dto.PageRequestDTO;
import com.example.movietalk.movie.dto.PageResultDTO;
import com.example.movietalk.movie.service.MovieService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/list")
    public void getMovieList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("영화 리스트 요청 {}", pageRequestDTO);

        PageResultDTO<MovieDTO> result = movieService.getMovieList(pageRequestDTO);
        model.addAttribute("result", result);
    }

    @GetMapping("/create")
    public void getCreate() {
        log.info("영화 추가 폼 요청");
    }

    @PostMapping("/create")
    public String postMethodName(MovieDTO movieDTO, RedirectAttributes rttr) {
        log.info("영화 추가 요청 {}", movieDTO);
        String title = movieService.register(movieDTO);
        rttr.addFlashAttribute("mno", title + "영화 등록 완료");
        return "redirect:/movie/list";
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(@RequestParam Long mno, Model model) {
        log.info("get or modify {}", mno);

        MovieDTO movieDTO = movieService.getRow(mno);
        model.addAttribute("dto", movieDTO);
    }

}
