package com.example.memo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.memo.dto.MemoDTO;
import com.example.memo.service.MemoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController // 데이터(json)만 주고 받기
@Log4j2
// @RequestMapping("/memo2")
@RequiredArgsConstructor
public class BasicController {
    private final MemoService memoService;

    @GetMapping("/hello")
    public String getHello() {
        return "Hello World"; // 문자열은 브라우저 해석 가능
    }

    @GetMapping("/sample1/{id}")
    public MemoDTO getRead(@PathVariable Long id) {
        MemoDTO dto = memoService.read(id);
        return dto;
    }

    @GetMapping("/list")
    public List<MemoDTO> getlist(Model model) {
        log.info("전체 메모 요청");
        List<MemoDTO> list = memoService.readAll();
        return list;
    }

}
