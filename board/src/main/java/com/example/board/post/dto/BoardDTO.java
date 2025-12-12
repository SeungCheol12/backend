package com.example.board.post.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDTO {
    private Long bno;

    private String title;

    private String content;

    private String writerEail; // 작성자 이메일
    private String writerName; // 작성자 이름

    private LocalDateTime createDateTime;
    private LocalDateTime updatedDateTime;

    private int replyCnt;
}
