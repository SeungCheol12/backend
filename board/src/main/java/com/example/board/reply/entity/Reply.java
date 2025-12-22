package com.example.board.reply.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.board.member.entity.Member;
import com.example.board.post.entity.BaseEntity;
import com.example.board.post.entity.Board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "board")
@Table(name = "replytbl")
@Entity // => 이 클래스는 테이블과 연동되어 있음
@EntityListeners(value = AuditingEntityListener.class)
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @Column(nullable = false)
    private String text;

    // GUSET => MEMBER 만 댓글 가능
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Member replyer;

    // Board
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bno")
    private Board board;

    public void changeText(String text) {
        this.text = text;
    }
}
