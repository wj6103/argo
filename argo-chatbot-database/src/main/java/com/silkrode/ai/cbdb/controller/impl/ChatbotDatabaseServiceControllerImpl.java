package com.silkrode.ai.cbdb.controller.impl;


import com.silkrode.ai.cbdb.controller.HbaseServiceRemoteController;
import com.silkrode.ai.cbdb.controller.MongoDBServiceRemoteController;
import com.silkrode.ai.common.dto.DataDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @implNote : integrate HBase service and mongoDB service.
 */
@RestController
@Api(value = "Chatbot Database Restful API.", consumes = "application/json", produces = "application/json", protocols = "http")
public class ChatbotDatabaseServiceControllerImpl {
    private final HbaseServiceRemoteController hbaseServiceRemoteController;
    private final MongoDBServiceRemoteController mongoDBServiceRemoteController;

    public ChatbotDatabaseServiceControllerImpl(HbaseServiceRemoteController hbaseServiceRemoteController, MongoDBServiceRemoteController mongoDBServiceRemoteController) {
        this.hbaseServiceRemoteController = hbaseServiceRemoteController;
        this.mongoDBServiceRemoteController = mongoDBServiceRemoteController;
    }

    @ApiOperation(value = "Insert Data", notes = "新增一筆資料", httpMethod = "POST")
    @PostMapping("/insert")
    public Object insert(@RequestBody DataDto dataDto) {
        if (dataDto.getCollection().equals("message_history"))
            return hbaseServiceRemoteController.insert(dataDto);
        else
            return mongoDBServiceRemoteController.insert(dataDto);
    }

    @ApiOperation(value = "Insert Many Data", notes = "新增多筆資料", httpMethod = "POST")
    @PostMapping("/insertMany")
    public Object insertMany(@RequestBody DataDto dataDto) {
        return mongoDBServiceRemoteController.insertMany(dataDto);
    }

    @ApiOperation(value = "Update Data", notes = "更新符合條件的第一筆資料", httpMethod = "POST")
    @PostMapping("/update")
    public Object update(@RequestBody DataDto dataDto) {
        return mongoDBServiceRemoteController.update(dataDto);
    }

    @ApiOperation(value = "Update Many Data", notes = "更新所有符合條件的資料", httpMethod = "POST")
    @PostMapping("/updateMany")
    public Object updateMany(@RequestBody DataDto dataDto) {
        return mongoDBServiceRemoteController.updateMany(dataDto);
    }

    @ApiOperation(value = "Query Data", notes = "查詢所有符合條件的資料", httpMethod = "POST")
    @PostMapping("/query")
    public Object query(@RequestBody DataDto dataDto) {
        if (dataDto.getCollection().equals("message_history"))
            return hbaseServiceRemoteController.query(dataDto);
        else
            return mongoDBServiceRemoteController.query(dataDto);
    }

    @ApiOperation(value = "Delete Data", notes = "刪除符合條件的第一筆資料", httpMethod = "POST")
    @PostMapping("/delete")
    public Object delete(@RequestBody DataDto dataDto) {
        return mongoDBServiceRemoteController.delete(dataDto);
    }

    @ApiOperation(value = "Delete Many Data", notes = "刪除符合條件的所有資料", httpMethod = "POST")
    @PostMapping("/deleteMany")
    public Object deleteMany(@RequestBody DataDto dataDto) {
        return mongoDBServiceRemoteController.deleteMany(dataDto);
    }

    @ApiOperation(value = "Count Data", notes = "計算符合條件資料的個數", httpMethod = "POST")
    @PostMapping("/count")
    public Object count(@RequestBody DataDto dataDto) {
        return mongoDBServiceRemoteController.count(dataDto);
    }
}
