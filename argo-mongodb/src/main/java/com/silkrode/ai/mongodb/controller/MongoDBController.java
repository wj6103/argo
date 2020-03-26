package com.silkrode.ai.mongodb.controller;

import com.silkrode.ai.common.dto.DataDto;
import com.silkrode.ai.mongodb.service.MongoDBService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
/**
 * @apiNote : mongoDB service controller
 * @version : 0.0.1
 * @param : dataDto (define in module common-utils )
 * @return : return mongoDB default message.
 */
@Api(value = "MongoDB Restful API.", consumes = "application/json", produces = "application/json", protocols = "http")
@RestController
@Slf4j
public class MongoDBController {
    private final MongoDBService mongoDBService;

    public MongoDBController(MongoDBService mongoDBService) {
        this.mongoDBService = mongoDBService;
    }

    @ApiOperation(value = "Insert Data", notes = "新增一筆資料", tags = "Insert", httpMethod = "POST")
    @PostMapping("/insert")
    public Object insert(@RequestBody DataDto dataDto) {
        log.debug("Data format is: {}", dataDto);
        return mongoDBService.insert(dataDto);
    }

    @ApiOperation(value = "Insert Many Data", notes = "新增多筆資料", tags = "Insert", httpMethod = "POST")
    @PostMapping("/insertMany")
    public Object insertMany(@RequestBody DataDto dataDto) {
        log.debug("Data format is: {}", dataDto);
        return mongoDBService.insertMany(dataDto);
    }

    @ApiOperation(value = "Update Data", notes = "更新符合條件的第一筆資料", tags = "Update", httpMethod = "POST")
    @PostMapping("/update")
    public Object update(@RequestBody DataDto dataDto) {
        log.debug("Data format is: {}", dataDto);
        return mongoDBService.update(dataDto);
    }

    @ApiOperation(value = "Update Many Data", notes = "更新所有符合條件的資料", tags = "Update", httpMethod = "POST")
    @PostMapping("/updateMany")
    public Object updateMany(@RequestBody DataDto dataDto) {
        log.debug("Data format is: {}", dataDto);
        return mongoDBService.updateMany(dataDto);
    }

    @ApiOperation(value = "Query Data", notes = "查詢所有符合條件的資料", tags = "Query", httpMethod = "POST")
    @PostMapping("/query")
    public Object query(@RequestBody DataDto dataDto) {
        log.debug("Data format is: {}", dataDto);
        return mongoDBService.query(dataDto);
    }

    @ApiOperation(value = "Delete Data", notes = "刪除符合條件的第一筆資料", tags = "Delete", httpMethod = "POST")
    @PostMapping("/delete")
    public Object delete(@RequestBody DataDto dataDto) {
        log.debug("Data format is: {}", dataDto);
        return mongoDBService.delete(dataDto);
    }

    @ApiOperation(value = "Delete Many Data", notes = "刪除符合條件的所有資料", tags = "Delete", httpMethod = "POST")
    @PostMapping("/deleteMany")
    public Object deleteMany(@RequestBody DataDto dataDto) {
        log.debug("Data format is: {}", dataDto);
        return mongoDBService.deleteMany(dataDto);
    }

    @ApiOperation(value = "Count Data", notes = "計算符合條件資料的個數", tags = "Query", httpMethod = "POST")
    @PostMapping("/count")
    public Object count(@RequestBody DataDto dataDto) {
        log.debug("Data format is: {}", dataDto);
        return mongoDBService.count(dataDto);
    }
}
