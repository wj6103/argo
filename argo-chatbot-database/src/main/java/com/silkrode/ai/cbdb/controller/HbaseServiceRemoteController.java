package com.silkrode.ai.cbdb.controller;

import com.silkrode.ai.cbdb.controller.hystrix.fallback.HbaseServiceFallback;
import com.silkrode.ai.common.dto.DataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @implSpec : consumer of HBase service
 */
@FeignClient(name= "hbase-service", fallback = HbaseServiceFallback.class)
@Primary
public interface HbaseServiceRemoteController {
    @PostMapping("/insert")
    Object insert(@RequestBody DataDto dataDto);
    @PostMapping("/query")
    Object query(@RequestBody DataDto dataDto);
}
