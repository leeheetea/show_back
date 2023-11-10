package com.showback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        final int MAX_AGE_SECS = 3600;
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 허용 주소
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("totalcount") // 노출할 헤더 추가
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }

}
