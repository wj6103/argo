package com.silkrode.ai.cbdb.controller.hystrix.fallback;

import com.silkrode.ai.cbdb.controller.RedisRemoteController;
import com.silkrode.ai.common.dto.DataDto;
import org.springframework.stereotype.Component;
/**
 * @implNote : define Redis service fallback function.
 */
@Component
public class RedisServiceFallback implements RedisRemoteController {

    @Override
    public Object set(DataDto dataDto) {
        return "service is not available.";
    }

    @Override
    public Object get(String path) {
        return "service is not available.";
    }
}
