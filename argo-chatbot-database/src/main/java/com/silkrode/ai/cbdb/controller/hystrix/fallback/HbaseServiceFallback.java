package com.silkrode.ai.cbdb.controller.hystrix.fallback;

import com.silkrode.ai.cbdb.controller.HbaseServiceRemoteController;
import com.silkrode.ai.common.dto.DataDto;
import org.springframework.stereotype.Component;

/**
 * @implNote : define HBase service fallback function.
 */
@Component
public class HbaseServiceFallback implements HbaseServiceRemoteController {
    @Override
    public Object insert(DataDto dataDto) {
        return "service is not available.";
    }

    @Override
    public Object query(DataDto dataDto) {
        return "service is not available.";
    }
}
