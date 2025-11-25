package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.demo.dto.Info;

@Log4j2
@Controller
public class HomeController {

// get 요청으로 들어올 때
    @GetMapping("/home")
    public void getHome() {
        log.info("home 요청");
        
    } 
    @GetMapping("/add")
    public String getAdd() {
        return "result";
    }
    @GetMapping("/calc")
    public void getMethodName() {
        log.info("calc get");
    }
    
    @PostMapping("/calc")
    public void postMethodName(int num1,int num2) {
       log.info("calc post {}", num1, num2);
        
    
    }
    @GetMapping("/info")
    public void getInfo() {
        log.info("info.html 호출");
    }

    // @PostMapping("/info")
    // public void postInfo(String username, int age, String addr, String tel) {
    //    log.info("info post");
    //    log.info("{},{},{},{}", username, age, addr, tel);
    // }
    @PostMapping("/info")
    public void postInfo(Info info) {
       log.info("info post");
       log.info("{},{},{},{}", info.getUsername(), info.getAge(), info.getAddr(), info.getTel());
    }
    
}