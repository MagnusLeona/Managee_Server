package com.magnus.project.managee.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration(proxyBeanMethods = false)
public class MagnusConfig {

    @Bean
    @Primary
    public Dog dog() {
        return new Dog();
    }

    @Bean("anotherDog")
    public Dog anotherDog() {
        return dog();
    }
}
