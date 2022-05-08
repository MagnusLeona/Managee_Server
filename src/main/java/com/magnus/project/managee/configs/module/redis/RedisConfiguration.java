package com.magnus.project.managee.configs.module.redis;

import com.magnus.project.managee.support.constants.Constants;
import com.magnus.project.managee.support.dicts.CommonDict;
import com.magnus.project.managee.support.dicts.Dict;
import io.lettuce.core.ReadFrom;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.time.Duration;

@Configuration
@PropertySource("classpath:properties/datasource.properties")
public class RedisConfiguration {

    @Value("${redis.host}")
    public String hostname;
    @Value("${redis.port}")
    public int port;
    @Value("${redis.timeout}")
    public int timeout;
    @Value("${redis.loginDatabase}")
    public int loginDatabase;
    @Value("${redis.cacheDatabase}")
    public int cacheDatabase;
    @Value("${redis.commonDatabase}")
    public int commonDatabase;

    @Bean
    public LettuceConnectionFactory commonConnectionFactory() {
        return getLettuceConnectionFactory(commonDatabase);
    }

    @Bean
    public LettuceConnectionFactory loginConnectionFactory() {
        return getLettuceConnectionFactory(loginDatabase);
    }

    @Bean
    public LettuceConnectionFactory cacheConnectionFactory() {
        return getLettuceConnectionFactory(cacheDatabase);
    }

    @Bean
    public RedisTemplate commonRedisTemplate() {
        RedisTemplate<String, Serializable> stringSerializableRedisTemplate = new RedisTemplate<>();
        stringSerializableRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringSerializableRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        stringSerializableRedisTemplate.setConnectionFactory(commonConnectionFactory());
        return stringSerializableRedisTemplate;
    }

    @Bean
    public RedisTemplate loginRedisTemplate() {
        RedisTemplate<String, Serializable> stringSerializableRedisTemplate = new RedisTemplate<>();
        stringSerializableRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringSerializableRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        stringSerializableRedisTemplate.setConnectionFactory(loginConnectionFactory());
        return stringSerializableRedisTemplate;
    }

    @Bean
    public RedisTemplate cacheRedisTemplate() {
        RedisTemplate<String, Serializable> stringSerializableRedisTemplate = new RedisTemplate<>();
        stringSerializableRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringSerializableRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        stringSerializableRedisTemplate.setConnectionFactory(cacheConnectionFactory());
        return stringSerializableRedisTemplate;
    }

    public LettuceConnectionFactory getLettuceConnectionFactory(int db) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostname);
        redisStandaloneConfiguration.setPort(port);
        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder().readFrom(ReadFrom.REPLICA_PREFERRED).commandTimeout(Duration.ofSeconds(timeout)).build();
            LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
        lettuceConnectionFactory.setDatabase(db);
        return lettuceConnectionFactory;
    }
}
