package com.example.memo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.memo.dto.MemoDTO;
import com.example.memo.repository.MemoRepository;
import com.example.memo.service.MemoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RequestMapping("/memo")
@Controller // template 필요
@Log4j2
public class MemoController {

    private final MemoService memoService;

    // MemoController(MemoRepository memoRepository) {
    // this.memoRepository = memoRepository;
    // }
    @ResponseBody // 리턴값은 데이터임
    @GetMapping("/hello")
    public String getHello() {
        return "Hello World"; // 문자열은 브라우저 해석 가능
    }

    @ResponseBody
    @GetMapping("/sample1/{id}")
    public MemoDTO getRead(@PathVariable Long id) {
        MemoDTO dto = memoService.read(id);
        return dto;
    }

    @GetMapping("/sample1/list")
    public ResponseEntity<List<MemoDTO>> getRead2() {

        List<MemoDTO> list = memoService.readAll();
        // ResponseEntity : 데이터 + 상태코드(200, 400, 500)
        return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/list")
    public void getlist(Model model) {
        log.info("전체 메모 요청");
        List<MemoDTO> list = memoService.readAll();
        model.addAttribute("list", list);
    }

    @GetMapping("/list2")
    public void getlist2() {
        log.info("전체 메모 요청");

    }

    @GetMapping({ "/read2", "/modify2" })
    public void getread2(Long id, Model model) {
        log.info("특정 메모 요청 {}", id);
        MemoDTO dto = memoService.read(id);
        model.addAttribute("dto", dto);
        model.addAttribute("id", id);
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(@RequestParam Long id, Model model) {
        log.info("메모 id {}", id);

        MemoDTO dto = memoService.read(id);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postModify(MemoDTO dto, RedirectAttributes rttr) {
        log.info("memo 수정 {}", dto);
        Long id = memoService.modify(dto);

        rttr.addAttribute("id", id);
        return "redirect:/memo/read";
    }

    @PostMapping("/remove")
    public String postRemove(@RequestParam Long id, RedirectAttributes rttr) {
        log.info("memo remove id {}", id);

        memoService.remove(id);

        rttr.addFlashAttribute("msg", "메모가 삭제되었습니다");
        // redirect 로 다시 list 주소로 들어가는 처리가 되어서 테이블이 출력된다
        return "redirect:/memo/list"; // redirect 가 없다면 그냥 list.html을 보여주기가 되어서
                                      // 아무것도 출력되지 않는다
    }

    @GetMapping("/create")
    public void getCreate(@ModelAttribute("dto") MemoDTO dto) {
        log.info("추가 페이지 요청");
    }

    @GetMapping("/create2")
    public void getCreate2(@ModelAttribute("dto") MemoDTO dto) {
        log.info("추가 rest 페이지 요청");
    }

    @PostMapping("/create")
    public String postCreate(@Valid @ModelAttribute("dto") MemoDTO dto, BindingResult result, RedirectAttributes rttr) {
        log.info("추가 요청 {}", dto);
        // 유효성 검증 조건에 일치하지 않는 경우
        if (result.hasErrors()) {
            return "/memo/create";
        }
        // 일치하는 경우
        Long id = memoService.insert(dto);
        // rttr.addAttribute("id", id);
        rttr.addFlashAttribute("msg", id + " 번 메모가 삽입되었습니다");
        return "redirect:/memo/list";
    }

}
