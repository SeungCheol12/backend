package com.example.memo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.memo.dto.MemoDTO;
import com.example.memo.service.MemoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/memo")
public class MemoRestController {
    private final MemoService memoService;

    // http://localhost:8080/memo + id
    @GetMapping("/{id}")
    public MemoDTO getRead(@PathVariable Long id) {
        MemoDTO dto = memoService.read(id);
        return dto;
    }

    // http://localhost:8080/memo + get
    @GetMapping("")
    public List<MemoDTO> getlist(Model model) {
        log.info("전체 메모 요청");
        List<MemoDTO> list = memoService.readAll();
        return list;
    }

    // @RequestBody : json => 자바 객체 매핑
    // http://localhost:8080/memo + post
    @PostMapping("")
    public ResponseEntity postCreate(@RequestBody MemoDTO memoDTO) {
        log.info("삽입 {}", memoDTO);

        Long id = memoService.insert(memoDTO);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    // put, delete 는 rest 에서만 쓴다
    @PutMapping("")
    public ResponseEntity<Long> put(@RequestBody MemoDTO memoDTO) {
        log.info("수정 {}", memoDTO);

        Long id = memoService.modify(memoDTO);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        log.info("삭제 {}", id);

        memoService.remove(id);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
