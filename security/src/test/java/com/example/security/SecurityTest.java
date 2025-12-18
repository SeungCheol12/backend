package com.example.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncoder() {
        String password = "1111";

        // 단방향 암호화
        String encodePass = passwordEncoder.encode(password);

        // PasswordEncoderFactories.createDelegatingPasswordEncoder(); =>
        // {bcrypt}$2a$10$6aDd9P6rndqsIfIgMukLcuTEfuROVgKME9K7moyT8W8f6oy.3nqF2

        // new BCryptPasswordEncoder(); =>
        // $2a$10$kPLmMgYXc6C14xUp990deuGxoXleZpXs1/aRIWNlSy3EYa/9bzgzy
        System.out.println("raw password " + password + "encode password " + encodePass);

        System.out.println(passwordEncoder.matches(password, encodePass)); // password, encodepassword 비교 true

        System.out.println(passwordEncoder.matches("2222", encodePass)); // false
    }
}
