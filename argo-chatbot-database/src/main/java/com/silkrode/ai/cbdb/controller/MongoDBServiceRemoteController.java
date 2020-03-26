package com.silkrode.ai.cbdb.controller;

import com.silkrode.ai.cbdb.controller.hystrix.fallback.MongoDBServiceFallback;
import com.silkrode.ai.common.dto.DataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @implSpec : consumer of mongoDB service
 */
@FeignClient(name= "mongodb-service" ,fallback = MongoDBServiceFallback.class)
@Primary
public interface MongoDBServiceRemoteController {

    @PostMapping("/insert")
    Object insert(@RequestBody DataDto dataDto);

    @PostMapping("/insertMany")
    Object insertMany(@RequestBody DataDto dataDto);

    @PostMapping("/update")
    Object update(@RequestBody DataDto dataDto);

    @PostMapping("/updateMany")
    Object updateMany(@RequestBody DataDto dataDto);

    @PostMapping("/query")
    Object query(@RequestBody DataDto dataDto);

    @PostMapping("/delete")
    Object delete(@RequestBody DataDto dataDto);

    @PostMapping("/deleteMany")
    Object deleteMany(@RequestBody DataDto dataDto);


    @PostMapping("/count")
    Object count(@RequestBody DataDto dataDto);


}
