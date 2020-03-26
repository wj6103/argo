package com.silkrode.ai.stream.controller;


import com.silkrode.ai.common.dto.DataDto;
import com.silkrode.ai.stream.service.impl.StreamServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @apiNote : Stream service controller
 * @version : 0.0.1
 * @param : dataDto (define in module common-utils )
 * @return : sendMessage method return user's input data.
 */
@Api(value = "Stream Restful API.", consumes = "application/json", produces = "application/json", protocols = "http")
@RestController
public class StreamController {
    private final StreamServiceImpl streamService;

    public StreamController(StreamServiceImpl streamService) {
        this.streamService = streamService;
    }

    @ApiOperation(value = "Send message", notes = "將資料送入消息隊列", httpMethod = "POST")
    @PostMapping("/topics")
    public Object sendMessage(@RequestBody @Validated DataDto dataDto) {
        Map check = (Map) dataDto.getData();
        Assert.isTrue(check.size() == 2 && check.containsKey("path") && check.containsKey("content"), "format error");
        Assert.isTrue(check.get("path").toString().startsWith("/"), "format error");
        streamService.sendMessage(dataDto);
        return dataDto;
    }
}
