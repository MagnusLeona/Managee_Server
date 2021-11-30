package com.magnus.project.managee.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class MagnusComponent {

    @Bean
    @Primary
    public Cat cat() {
        return new Cat();
    }

    @Bean("anotherCat")
    public Cat anotherCat() {
        return cat();
    }
}
