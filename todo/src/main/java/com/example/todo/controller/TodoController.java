package com.example.todo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.dto.PageRequestDTO;
import com.example.todo.dto.PageResultDTO;
import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.Todo;
import com.example.todo.service.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Response Todos", description = "Response Todo API")
@RestController
@RequestMapping("/todos")
@Log4j2
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    // 전체조회 http://localhost:8080/todos?completed= + Get
    // 완료조회 http://localhost:8080/todos?completed=true + Get
    // 미완료조회 http://localhost:8080/todos?completed=false + Get
    // @CrossOrigin(origins = "http://localhost:5173") : 컨트롤러 단위로 cors 설정
    @Operation(summary = "todo 조회", description = "todo 전체 조회 API - 완료 여부 포함 가능")
    @GetMapping("")
    public PageResultDTO<TodoDTO> getTodoList(@RequestParam(required = false) Boolean completed, PageRequestDTO dto) {
        log.info("조회 {} " + completed);
        PageResultDTO<TodoDTO> list = todoService.findCompledTodos(completed, dto);
        return list;
    }

    // 입력 http://localhost:8080/todos/add + Post
    @Operation(summary = "todo 입력", description = "todo 입력 API")
    @PostMapping("/add")
    public Long postTodo(@RequestBody TodoDTO dto) {
        log.info("삽입 {}", dto);
        Long id = todoService.create(dto);

        return id;
    }

    // 수정 http://localhost:8080/todos/1 + Put
    @Operation(summary = "todo 수정", description = "todo 수정 API")
    @PutMapping("/{id}")
    public Long putMethodName(
            @Parameter(description = "수정할 todo id 값", example = "1", required = true) @PathVariable Long id,
            @RequestBody TodoDTO dto) {
        log.info("수정 id: {}, dto: {}", id, dto);
        dto.setId(id);
        return todoService.update(dto);
    }

    // 삭제 http://localhost:8080/todos/1 + Delete
    @Operation(summary = "todo 삭제", description = "todo 삭제 API")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(
            @Parameter(description = "삭제할 todo id 값", example = "1", required = true) @PathVariable Long id) {
        log.info("삭제 id: {} ", id);
        todoService.delete(id);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
