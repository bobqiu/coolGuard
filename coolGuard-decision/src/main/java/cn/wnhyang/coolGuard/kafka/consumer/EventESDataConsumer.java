package cn.wnhyang.coolGuard.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author wnhyang
 * @date 2024/2/29
 **/
@Service
@Slf4j
public class EventESDataConsumer {

    @KafkaListener(topics = "event-es-data", groupId = "event-es-data-consumers")
    public void consume(String message) {
        // TODO 存储到ES
        log.info("consume message: {}", message);
    }
}
