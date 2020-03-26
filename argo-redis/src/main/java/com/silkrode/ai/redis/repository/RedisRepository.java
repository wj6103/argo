package com.silkrode.ai.redis.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * @implNote  : The repository of Redis.
 * @param : input data from dataDto and query condition.
 * @return : getData method return query result.
 */
@Repository
@Slf4j
public class RedisRepository {
    private final RedisTemplate redisTemplate;

    public RedisRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(Object data){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        ValueOperations<String, Object> vo =  redisTemplate.opsForValue();
        vo.set(((Map)data).get("path").toString(), ((Map)data).get("content"));
    }

    public Object getData(String key)
    {
        String nPath = "/"+key;
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        Object r = redisTemplate.opsForValue().get(nPath);

        return r;
    }
}
