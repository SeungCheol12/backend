package com.example.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/todos")
@Log4j2
public class TodoController {
    // 전체조회 http://localhost:8080/todos + Get
    // 완료조회 http://localhost:8080/todos?completed=true + Get
    // 입력 http://localhost:8080/todos/add + Post
    // 수정 http://localhost:8080/todos/1 + Put
    // 삭제 http://localhost:8080/todos/1 + Delete

}
