package com.example.movietalk.member.dto;

import com.example.movietalk.member.entity.constant.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CustomUserDTO {
    private Long mid;

    @Email(message = "유효한 이메일 형식이 아닙니다")
    @NotNull(message = "이메일을 입력해주세요")
    private String email;

    @NotNull(message = "비밀번호를 입력해주세요")
    private String password;

    @NotNull(message = "닉네임을 입력해주세요")
    private String nickname;

    private Role role;
}
