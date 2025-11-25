package com.example.demo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.LoginDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@Log4j2
@RequestMapping("/member")
public class LoginController {
    @GetMapping("/login")
    public void getMethodName() {
       log.info("로그인 페이지 요청");
    }
    // @PostMapping("/login")
    // public void postLogin(String id, int password) {
    //    log.info("아이디 : {}, 비밀번호 : {}", id, password);
    // }
    @PostMapping("/login")
    public void postMethodName(@ModelAttribute("login") LoginDTO login) {
        log.info("login post");
       log.info("{} ",login);
    }

    // @RequestParam : http 요청의 파라미터를 컨트롤러 메소드의 매개변수로 바인딩
    // @GetMapping("path")
    // public String getMethodName(@RequestParam String param) {
    //     return new String();
    // }
    
}
