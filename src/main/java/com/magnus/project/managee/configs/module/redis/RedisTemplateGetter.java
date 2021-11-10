package com.magnus.project.managee.configs.module.redis;

import com.magnus.project.managee.support.dicts.CommonDict;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisTemplateGetter implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        applicationContext = ac;
    }

    public static RedisTemplate getRedisTemplate(String name) {
        return (RedisTemplate) applicationContext.getBean(name);
    }
}
