package com.silkrode.ai.hbase.controller;


import com.silkrode.ai.common.dto.DataDto;
import com.silkrode.ai.hbase.service.impl.HbaseServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * @apiNote : Hbase service controller
 * @version : 0.0.1
 * @param : dataDto (define in module common-utils )
 * @return : insert method return user's input data.
 *           query method return query result.
 */
@RestController
@Api(value = "HBase Restful API.", consumes = "application/json", produces = "application/json", protocols = "http")
@Slf4j
public class HbaseController {
    private final HbaseServiceImpl hbaseService;

    public HbaseController(HbaseServiceImpl hbaseService) {this.hbaseService = hbaseService;}

    @ApiOperation(value = "Insert Data", notes = "新增一筆資料", tags = "Insert", httpMethod = "POST")
    @PostMapping("/insert")
    public Object insert(@RequestBody DataDto dataDto) {
        hbaseService.insert(dataDto);
        return dataDto;
    }

    @ApiOperation(value = "Query Data", notes = "查詢所有符合條件的資料", tags = "Query", httpMethod = "POST")
    @PostMapping("/query")
    public Object query(@RequestBody DataDto dataDto) {
        Map result = hbaseService.query(dataDto);
        Assert.notEmpty((List)result.get("message"),"data not found.");
        return result;
    }
}
