package com.magnus.project.managee.configs.module.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonObjectMapper {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
