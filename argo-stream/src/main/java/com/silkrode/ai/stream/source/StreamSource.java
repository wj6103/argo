package com.silkrode.ai.stream.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @apiNote : define kafka topic
 */
public interface StreamSource {

    String OUTPUT = "chatbot";

    @Output(StreamSource.OUTPUT)
    MessageChannel output();

}
