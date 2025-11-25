package com.example.web.Controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Log4j2
public class BoardController {
    @GetMapping("/board/list")
    public void getList() {
       log.info("/board/list 요청");
    }
    
}
