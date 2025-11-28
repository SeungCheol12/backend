package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EntityListeners(value = AuditingEntityListener.class)
@Builder
// @Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "boardtbl")
@Entity // => 이 클래스는 테이블과 연동되어 있음
public class Board {
    // id(자동순번), 제목(title), 내용(content-1500), 작성자(writer-20)
    // 작성일, 수정일
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1500)
    private String content;

    @Column(nullable = false, length = 20)
    private String writer;

    @CreatedDate // spring boot 설정 후 삽입
    private LocalDateTime createDateTime2;

    @LastModifiedDate
    private LocalDateTime updatedDateTime;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}
