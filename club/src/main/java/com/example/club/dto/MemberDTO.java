package com.example.club.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class MemberDTO extends User {
    public MemberDTO(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.fromSocial = fromSocial;
        this.email = username;
    }

    // member entity 정보 + 인증정보 => extends User
    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

}
