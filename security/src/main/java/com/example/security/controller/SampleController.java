package com.example.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

    // http://localhost:8080 : 모두에게 개방
    // http://localhost:8080/sample/guest : 모두에게 개방
    // http://localhost:8080/sample/member : 멤버에게 개방
    // http://localhost:8080/sample/admin : admin에게 개방

    @GetMapping("/guest")
    public void getGuest() {
        log.info("guest 요청");
    }

    @GetMapping("/member")
    public void getMember() {
        log.info("member 요청");
    }

    @GetMapping("/admin")
    public void getAdmin() {
        log.info("admin 요청");
    }

    @GetMapping("/info")
    public void getInfo() {
        log.info("info 요청");
    }

    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인 form 요청");
    }

}
