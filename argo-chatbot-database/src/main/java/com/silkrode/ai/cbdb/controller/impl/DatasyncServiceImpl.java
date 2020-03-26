package com.silkrode.ai.cbdb.controller.impl;

import com.silkrode.ai.cbdb.controller.RedisRemoteController;
import com.silkrode.ai.cbdb.controller.StreamServiceRemoteController;
import com.silkrode.ai.common.dto.DataDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @implNote : integrate Stream service and Redis service.
 */
@Api(value = "Datasync Restful API.", consumes = "application/json", produces = "application/json", protocols = "http")
@RestController
public class DatasyncServiceImpl {
    private final RedisRemoteController redisRemoteController;
    private final StreamServiceRemoteController streamServiceRemoteController;

    public DatasyncServiceImpl(RedisRemoteController redisRemoteController, StreamServiceRemoteController streamServiceRemoteController) {
        this.redisRemoteController = redisRemoteController;
        this.streamServiceRemoteController = streamServiceRemoteController;
    }

    @ApiOperation(value = "Set", notes = "儲存資料", httpMethod = "POST")
    @PostMapping("/topics/{value}")
    public Object set(@RequestBody DataDto dataDto){
        return redisRemoteController.set(dataDto);
    }

    @ApiOperation(value = "Get", notes = "取得最後一筆新增資料", httpMethod = "GET")
    @GetMapping("/topics/{value}")
    public Object get(@PathVariable("value") String path){
        return redisRemoteController.get(path);
    }

    @ApiOperation(value = "Set Message", notes = "將資料送入消息隊列", httpMethod = "POST")
    @PostMapping("/topics")
    public Object sendMessage(@RequestBody DataDto dataDto){
        return streamServiceRemoteController.sendMessage(dataDto);
    }
}
