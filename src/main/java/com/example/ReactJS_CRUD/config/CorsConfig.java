package com.example.ReactJS_CRUD.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//vaibhav
        registry.addMapping("/**")
        		.allowedOrigins(
        				"http://localhost:3000",
        				"http://127.0.0.1:5500",
        				"https://boilingly-unsignificant-lydia.ngrok-free.dev"
        				)
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowCredentials(true);
    }
}