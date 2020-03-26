package com.silkrode.ai.cbdb.controller;

import com.silkrode.ai.cbdb.controller.hystrix.fallback.StreamServiceFallback;
import com.silkrode.ai.common.dto.DataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @implSpec : consumer of Stream service
 */
@FeignClient(name= "stream-service",fallback = StreamServiceFallback.class)
@Primary
public interface StreamServiceRemoteController {

    @PostMapping("/tpoics")
    Object sendMessage(@RequestBody DataDto dataDto);
}
