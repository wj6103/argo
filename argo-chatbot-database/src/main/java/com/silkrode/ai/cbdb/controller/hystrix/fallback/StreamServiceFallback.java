package com.silkrode.ai.cbdb.controller.hystrix.fallback;

import com.silkrode.ai.cbdb.controller.StreamServiceRemoteController;
import com.silkrode.ai.common.dto.DataDto;
import org.springframework.stereotype.Component;
/**
 * @implNote : define Stream service fallback function.
 */
@Component
public class StreamServiceFallback implements StreamServiceRemoteController {
    @Override
    public Object sendMessage(DataDto dataDto) {
        return "service is not available.";
    }
}
