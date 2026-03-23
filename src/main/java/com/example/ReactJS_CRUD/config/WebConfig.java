package com.example.ReactJS_CRUD.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // ✅ Correct for Windows
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///C:/uploads/");

        // Optional
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:///C:/uploads/");
    }
}