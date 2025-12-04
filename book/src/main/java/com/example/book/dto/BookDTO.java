package com.example.book.dto;

import groovy.transform.builder.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BookDTO {
    private Long id;
    @NotBlank(message = "ISBN은 필수입니다")
    private String isbn;
    @NotBlank(message = "도서명을 입력해주세요")
    private String title;

    @NotNull(message = "가격을 입력해주세요")
    private Integer price;

    @NotBlank(message = "저자를 입력해주세요")
    private String author;

    private String description;
}
