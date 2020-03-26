package com.silkrode.ai.stream.repository.impl;

import com.silkrode.ai.stream.repository.StreamRepository;
import com.silkrode.ai.stream.source.StreamSource;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Repository;


/**
 * @implNote  : Implement Stream repository.
 * @param : input data from dataDto.
 */
@EnableBinding(StreamSource.class)
@Repository
public class StreamRepositoryImpl  implements  StreamRepository{

    private final  StreamSource streamSource;

    public StreamRepositoryImpl(StreamSource streamSource) {
        this.streamSource = streamSource;
    }

    public void sendMessage (Object data) {
        streamSource.output().send(MessageBuilder.withPayload(data).build());
    }


}
