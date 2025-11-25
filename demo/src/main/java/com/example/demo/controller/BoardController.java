package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Log4j2
@RequestMapping("/board")
public class BoardController {
    @GetMapping("/add")
    public void getAdd() {
        log.info("/board/add 요청");
    }

    @GetMapping("/modify")
    public void getModify() {
        log.info("/board/modify 요청");
    }
    
    @GetMapping("/read")
    public void getRead(@ModelAttribute("no") int no, Model model) {
        log.info("/board/read 요청 {}", no);
        // model.addAttribute("no", no);
    }
    
}
