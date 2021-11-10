package com.magnus.project.managee.configs.module.mybatis.configs;

import com.magnus.project.managee.configs.module.redis.RedisTemplateGetter;
import com.magnus.project.managee.support.dicts.CommonDict;
import org.apache.ibatis.cache.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MybatisRedisCache implements Cache {

    private String id;
    private ReadWriteLock reentrantLock = new ReentrantReadWriteLock();
    private Logger logger = LogManager.getLogger(MybatisRedisCache.class);
    private RedisTemplate redisTemplate;

    {
        redisTemplate = RedisTemplateGetter.getRedisTemplate(CommonDict.REDIS_TEMPLATE_SQLCACHE.getName());
    }

    public MybatisRedisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object o, Object o1) {
        try {
            logger.debug("MybatisRedisCache putObject: [id: {}, key: {}, value: {}]", this.id, o.toString(), o1);
            redisTemplate.opsForHash().put(this.id, o.toString(), o1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getObject(Object o) {
        try {
            logger.debug("MybatisRedisCache getObject: [id: {}, key: {}]", this.id, o.toString());
            Object o1 = redisTemplate.opsForHash().get(this.id, o.toString());
            return o1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object removeObject(Object o) {
        try {
            redisTemplate.opsForHash().delete(this.id, o.toString());
            logger.debug("MybatisRedisCache removeObject: [id: {},key: {}]", this.id, o.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void clear() {
        try {
            redisTemplate.delete(this.id);
            logger.debug("MybatisRedisCache delete: [id: {}]", this.id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getSize() {
        try {
            Long size = redisTemplate.opsForHash().size(this.id);
            logger.debug("MybatisRedisCache getSize: [id: {}, size: {}]", id, size.intValue());
            return size.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return reentrantLock;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
