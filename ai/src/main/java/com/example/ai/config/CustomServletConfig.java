package com.example.ai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//  CORS(Cross-Origin Resource Sharing) : 전역 설정
@Configuration
public class CustomServletConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
        registry.addMapping("/api/**") // /todos/**
                .allowedOrigins("*") // http://localhost:5173/
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
                .maxAge(300)
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type");
    }
}
