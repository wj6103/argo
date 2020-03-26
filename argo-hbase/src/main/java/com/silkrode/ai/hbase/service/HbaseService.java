package com.silkrode.ai.hbase.service;

import com.alibaba.fastjson.JSONObject;
import com.silkrode.ai.common.dto.DataDto;
/**
 * @implSpec  : define HBase service
 */
public interface HbaseService {
    void insert(DataDto dataDto);
    JSONObject query(DataDto dataDto);
}
