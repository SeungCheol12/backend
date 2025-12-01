package com.example.memo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.memo.dto.MemoDTO;
import com.example.memo.repository.MemoRepository;
import com.example.memo.service.MemoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RequestMapping("/memo")
@Controller
@Log4j2
public class MemoController {

    private final MemoService memoService;

    // MemoController(MemoRepository memoRepository) {
    // this.memoRepository = memoRepository;
    // }

    @GetMapping("/list")
    public void getlist(Model model) {
        log.info("전체 메모 요청");
        List<MemoDTO> list = memoService.readAll();
        model.addAttribute("list", list);
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

    @PostMapping("/create")
    public String postCreate(@Valid @ModelAttribute("dto") MemoDTO dto, BindingResult result, RedirectAttributes rttr) {
        log.info("추가 요청 {}", dto);
        // 유효성 검증 조건에 일치하지 않는 경우
        if (result.hasErrors()) {
            return "/memo/create";
        }
        // 일치하는 경우
        Long id = memoService.insert(dto);
        rttr.addFlashAttribute("msg", id + " 번 메모가 삽입되었습니다");
        return "redirect:/memo/list";
    }

}
