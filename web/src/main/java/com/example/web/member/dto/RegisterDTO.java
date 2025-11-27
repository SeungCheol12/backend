package com.example.web.member.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterDTO {
    // @Length(min = 4, max = 8, message = "아이디를 4 ~ 8 자리 사이로 입력해주세요")
    // @NotBlank(message = "")
    @Pattern(regexp = "(?=^[A-Z])(?=.+[\\d])[A-Za-z\\d]{5,12}", message = "아이디를 대문자, 숫자를 포함하여 5 ~ 12 자리 사이로 입력해주세요")
    private String id;

    // @Length(min = 4, max = 8, message = "비밀번호를 4 ~ 8 자리 사이로 입력해주세요")
    // @NotEmpty(message = "")
    @Pattern(regexp = "(?=^[A-Z])(?=.+[\\d])(?=.+[#$%@!])[A-Za-z\\d#$%@!]{5,12}", message = "비밀번호를 대문자, 특수문자를 포함하여 5 ~ 12 자리 사이로 입력해주세요")
    private String password;

    @Email(message = "이메일 형식에 맞게 작성해주세요")
    @NotEmpty(message = "이메일을 입력해주세요")
    private String email;

    @Pattern(regexp = "^[가-힣]{2,6}$", message = "이름을 2 ~ 6 자리 사이로 입력해주세요")
    private String name;

    @Max(value = 120, message = "120세 이하여야 합니다")
    @Min(value = 19, message = "19세 이상이어야 합니다")
    @NotNull(message = "나이를 입력해주세요")
    private Integer age;
}
