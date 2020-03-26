package com.silkrode.ai.redis.controller;


import com.silkrode.ai.common.dto.DataDto;
import com.silkrode.ai.redis.service.impl.RedisServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @apiNote : Redis service controller
 * @version : 0.0.1
 * @param : dataDto (define in module common-utils )
 * @return : savetopic method return user's input data.
 *           get method return query result.
 */
@Api(value = "Redis Restful API.", consumes = "application/json", produces = "application/json", protocols = "http")
@RestController
@RequestMapping("/topics")
public class RedisController {
    private final RedisServiceImpl redisService;

    public RedisController(RedisServiceImpl redisService) {
        this.redisService = redisService;
    }

    @ApiOperation(value = "Save topic", notes = "儲存資料", httpMethod = "POST")
    @PostMapping("/{value}")
    public Object savetopic(@RequestBody DataDto dataDto) {
        redisService.set(dataDto);
        return dataDto;
    }

    @ApiOperation(value = "Get", notes = "取得最後一筆新增資料", httpMethod = "GET")
    @GetMapping("/{value}")
    public Object get(@PathVariable("value") String path){
        return redisService.get(path);
    }
}
