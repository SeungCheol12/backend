package com.example.club.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.club.handler.LoginSuccessHandler;

import lombok.extern.log4j.Log4j2;

@EnableWebSecurity // 모든 웹 요청에 대해 Security Filter Chain 적용
@Configuration // 스프링 설정 클래스
@Log4j2
public class SecurityConfig {

    // 시큐리티 설정 클래스

    @Bean // 객체 생성
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/assets/**", "/member/auth", "/img/**").permitAll()
                .requestMatchers("/member/**").hasRole("USER")
                .requestMatchers("/manager/**").hasAnyRole("MANAGER")
                .requestMatchers("/admin/**").hasAnyRole("ADMIN"))
                // .httpBasic(Customizer.withDefaults()); // http 기본적인 방식
                // .formLogin(Customizer.withDefaults()) // 기본 로그인 화면 보여주기
                .formLogin(login -> login.loginPage("/member/login").permitAll() // 폼 로그인
                        // .defaultSuccessUrl("/", true)) // 로그인 성공 시 가는 기본 주소
                        .successHandler(loginSuccessHandler())) // 로그인 성공 시 가는 주소
                .oauth2Login(login -> login.successHandler(loginSuccessHandler())) // 소셜 로그인
                .logout(logout -> logout.logoutUrl("/member/logout") // 로그아웃 post 로 처리
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"));

        return http.build();
    }

    @Bean
    LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // 운영, 실무, 여러 암호화 알고리즘 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // 연습, 단일 알고리즘 사용
        // return new BCryptPasswordEncoder();
    }

    // 임시 User 생성
    // @Bean
    // UserDetailsService users() {
    // UserDetails user = User.builder()
    // .username("user1")
    // .password("{bcrypt}$2a$10$4SH/ba27VEGoqdsV2rNAXuJ9EE6Aue0uVjajc9.dTiwFILrho69fW")
    // .roles("MEMBER")
    // .build();

    // UserDetails admin = User.builder()
    // .username("admin")
    // .password("{bcrypt}$2a$10$4SH/ba27VEGoqdsV2rNAXuJ9EE6Aue0uVjajc9.dTiwFILrho69fW")
    // .roles("MEMBER", "ADMIN")
    // .build();
    // return new InMemoryUserDetailsManager(user, admin);
    // }
}
