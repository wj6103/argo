package com.silkrode.ai.redis.service.impl;

import com.silkrode.ai.common.dto.DataDto;
import com.silkrode.ai.redis.repository.RedisRepository;
import com.silkrode.ai.redis.service.RedisService;
import org.springframework.stereotype.Service;

/**
 * @implNote  : Implement Redis service including insert and query two functions.
 * @param : dataDto from Redis controller.
 * @return : get method returns query result.
 */
@Service
public class RedisServiceImpl implements RedisService {
    private  final RedisRepository redisRepository;

    public RedisServiceImpl(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @Override
    public void set(DataDto dataDto) {
        redisRepository.set(dataDto.getData());
    }

    @Override
    public Object get(String key) {
        return redisRepository.getData(key);
    }
}
