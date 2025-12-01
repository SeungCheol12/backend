package com.example.memo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.memo.dto.MemoDTO;

@Log4j2
@Controller
public class HomeController {
    @GetMapping("/")
    public String getHome() {
        return "main";
    }

}
