package com.silkrode.ai.cbdb.controller.hystrix.fallback;

import com.silkrode.ai.cbdb.controller.MongoDBServiceRemoteController;
import com.silkrode.ai.common.dto.DataDto;
import org.springframework.stereotype.Component;
/**
 * @implNote : define mongoDB service fallback function.
 */
@Component
public class MongoDBServiceFallback implements MongoDBServiceRemoteController {
    @Override
    public Object insert(DataDto dataDto) {return "service is not available."; }

    @Override
    public Object insertMany(DataDto dataDto) {
        return "service is not available.";
    }

    @Override
    public Object update(DataDto dataDto) {
        return "service is not available.";
    }

    @Override
    public Object updateMany(DataDto dataDto) {
        return "service is not available.";
    }

    @Override
    public Object query(DataDto dataDto) {
        return "service is not available.";
    }

    @Override
    public Object delete(DataDto dataDto) {
        return "service is not available.";
    }

    @Override
    public Object deleteMany(DataDto dataDto) {
        return "service is not available.";
    }

    @Override
    public Object count(DataDto dataDto) {
        return "service is not available.";
    }
}
