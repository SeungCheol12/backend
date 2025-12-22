package com.example.board.member.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.board.member.constant.MemberRole;
import com.example.board.post.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
@ToString
@Table(name = "board_member")
@Entity // => 이 클래스는 테이블과 연동되어 있음
@EntityListeners(value = AuditingEntityListener.class)
public class Member extends BaseEntity {

    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private boolean fromSocial;

    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<MemberRole> roles = new HashSet<>();

    public void addMemberRole(MemberRole role) {
        roles.add(role);
    }
}
