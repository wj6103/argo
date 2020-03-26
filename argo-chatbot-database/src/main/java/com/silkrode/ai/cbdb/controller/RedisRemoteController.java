package com.silkrode.ai.cbdb.controller;

import com.silkrode.ai.cbdb.controller.hystrix.fallback.RedisServiceFallback;
import com.silkrode.ai.common.dto.DataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @implSpec : consumer of Redis service
 */
@FeignClient(name= "redis-service",fallback = RedisServiceFallback.class)
@Primary
public interface RedisRemoteController {

    @PostMapping("/topics/{value}")
    Object set(@RequestBody DataDto dataDto);

    @GetMapping("/topics/{value}")
    Object get(@PathVariable("value") String path);
}
