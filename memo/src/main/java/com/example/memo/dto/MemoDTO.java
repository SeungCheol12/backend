package com.example.memo.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MemoDTO {
    private Long id;

    @NotBlank(message = "내용을 입력해주세요")
    private String text;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

}
