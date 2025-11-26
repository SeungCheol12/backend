package com.example.web.member.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterDTO {
    @Length(min = 4, max = 8, message = "아이디를 4 ~ 8 자리 사이로 입력해주세요")
    @NotBlank(message = "")
    private String id;

    @Length(min = 4, max = 8, message = "비밀번호를 4 ~ 8 자리 사이로 입력해주세요")
    @NotEmpty(message = "")
    private String password;

    @NotEmpty(message = "이메일 형식에 맞게 작성해주세요")
    private String email;
}
