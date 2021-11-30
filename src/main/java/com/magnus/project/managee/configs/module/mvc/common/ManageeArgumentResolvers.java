package com.magnus.project.managee.configs.module.mvc.common;

import com.magnus.project.managee.configs.module.mvc.argsresolvers.UserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.List;

@Configuration
public class ManageeArgumentResolvers implements WebMvcConfigurer {

    @Autowired
    RedisTemplate loginRedisTemplate;

    @Bean
    public UserArgumentResolver userArgumentResolver() {
        UserArgumentResolver userArgumentResolver = new UserArgumentResolver();
        userArgumentResolver.setRedisTemplate(loginRedisTemplate);
        return userArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userArgumentResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加自定义的静态资源映射器
        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/");
    }
}
