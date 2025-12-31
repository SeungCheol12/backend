package com.example.movietalk.member.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AuthUserDTO extends User {
    private CustomUserDTO customeUserDTO;
    // Collection : List, Set
    // List<SimpleGrantedAutjority> list = new ArrayList<>();
    // list.add("ROLE_");

    // List.of(new CustomUserDTO())
    public AuthUserDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthUserDTO(CustomUserDTO customeUserDTO) {
        super(customeUserDTO.getEmail(), customeUserDTO.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + customeUserDTO.getRole())));
        this.customeUserDTO = customeUserDTO;
    }

}
