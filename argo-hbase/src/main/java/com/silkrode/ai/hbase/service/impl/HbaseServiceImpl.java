package com.silkrode.ai.hbase.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.silkrode.ai.common.dto.DataDto;
import com.silkrode.ai.hbase.repository.HbaseRepository;
import com.silkrode.ai.hbase.service.HbaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @implNote  : Implement HBase service including insert and query two functions.
 * @param : dataDto from HBase controller.
 * @return : query method returns query result.
 */
@Service
public class HbaseServiceImpl implements HbaseService {
    private final HbaseRepository hbaseRepository;

    public HbaseServiceImpl(HbaseRepository hbaseRepository) {this.hbaseRepository = hbaseRepository;}


    @Override
    public void insert(DataDto dataDto) {
        hbaseRepository.insert(dataDto.getData());
    }

    @Override
    public JSONObject query(DataDto dataDto) {
        List<Result> getQueryResult = hbaseRepository.query(dataDto.getFilter());
        JSONObject result = new JSONObject();
        List<JSONObject> messageData = new ArrayList();
        for(Result r : getQueryResult)
        {
            JSONObject getMessageData = new JSONObject();
            getMessageData.put("user_id",new String(r.getValue("info".getBytes(),"user_id".getBytes())));
            getMessageData.put("bot_id",new String(r.getValue("info".getBytes(),"bot_id".getBytes())));
            getMessageData.put("source_from",new String(r.getValue("info".getBytes(),"source_from".getBytes())));
            getMessageData.put("message",new String(r.getValue("info".getBytes(),"message".getBytes())));
            getMessageData.put("from_user",new String(r.getValue("info".getBytes(),"from_user".getBytes())));
            getMessageData.put("created",new String(r.getValue("info".getBytes(),"created".getBytes())));
            getMessageData.put("updated",new String(r.getValue("info".getBytes(),"updated".getBytes())));
            if(r.size()>7)
                getMessageData.put("from",new String(r.getValue("info".getBytes(),"from".getBytes())));
            else
                getMessageData.put("from","");
            messageData.add(getMessageData);

        }

        result.put("message",messageData);
        return result;
    }
}
