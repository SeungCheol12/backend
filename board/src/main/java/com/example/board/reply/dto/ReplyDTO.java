package com.example.board.reply.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class ReplyDTO {
    private Long rno;

    private String text;

    private String replyerEmail;
    private String replyerName;

    private Long bno;

    private LocalDateTime createDateTime;
    private LocalDateTime updatedDateTime;
}
