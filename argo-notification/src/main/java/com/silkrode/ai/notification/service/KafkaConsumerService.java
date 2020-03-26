package com.silkrode.ai.notification.service;

import com.silkrode.ai.notification.pojo.Letter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumerService {

    private SimpMessagingTemplate template;

    @KafkaListener(topics = "${subscribe.topic}")
    public void consume(@Payload Letter letter) {
        log.debug("Letter: " + letter);
        if (!StringUtils.isEmpty(letter.getPath())) {
            template.convertAndSend("/topic" + letter.getPath(), letter.getContent());
        }
    }
}
