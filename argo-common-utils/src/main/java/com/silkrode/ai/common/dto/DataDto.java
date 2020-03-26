package com.silkrode.ai.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@ApiModel(value = "Data DTO", description = "接收資料的物件")
@Data
public class DataDto {

    @ApiModelProperty(name = "collection", value = "表名或集合名", required = true, example = "agent")
    @NotEmpty(message = "Collection may not be empty.")
    String collection;

    @ApiModelProperty(name = "data", value = "要新增或更新的資料",
            example = "{\n" +
                    "    \"id\": \"agent-id-01\",\n" +
                    "    \"title\": \"思華智能客服 - 1\"\n" +
                    "}")
    Object data;

    @ApiModelProperty(name = "filter", value = "過濾的條件",
            example = "{\n" +
                    "    \"id\": \"agent-id-01\"\n" +
                    "}")
    Map<String, Object> filter;

    @ApiModelProperty(name = "projection", value = "過濾資料返回的欄位",
            example = "{\n" +
                    "\t\"_id\": 0\n" +
                    "}")
    Map<String, Boolean> projection;

    @ApiModelProperty(name = "sort", value = "排序返回的資料",
            example = "{\n" +
                    "\t\"title\": 0\n" +
                    "}")
    Map<String, Boolean> sort;

    @ApiModelProperty(name = "limit", value = "限制返回的個數",
            example = "3")
    Integer limit;

    @ApiModelProperty(name = "skip", value = "跳過的個數",
            example = "1")
    Integer skip;
}
