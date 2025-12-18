package com.example.club.entity;

import java.util.HashSet;
import java.util.Set;

import com.example.club.entity.constant.ClubMemberRole;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "club_member")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class Member {
    @Id
    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<ClubMemberRole> roles = new HashSet<>();

    public void addMemberRole(ClubMemberRole role) {
        roles.add(role);
    }
}
