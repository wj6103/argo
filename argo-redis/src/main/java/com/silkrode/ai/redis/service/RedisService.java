package com.silkrode.ai.redis.service;

import com.silkrode.ai.common.dto.DataDto;

/**
 * @implSpec  : define Redis service
 */
public interface RedisService {
    void set(DataDto dataDto);
    Object get(String key);
}
