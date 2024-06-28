package cn.wnhyang.coolGuard.kafka.consumer;

import cn.wnhyang.coolGuard.constant.KafkaConstant;
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

    @KafkaListener(topics = KafkaConstant.EVENT_ES_DATA, groupId = KafkaConstant.EVENT_ES_DATA_GROUP)
    public void consume(String message) {
        // TODO 存储到ES
        log.info("consume message: {}", message);
    }
}
