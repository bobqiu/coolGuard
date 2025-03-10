package cn.wnhyang.coolGuard.decision.kafka.consumer;

import cn.wnhyang.coolGuard.decision.constant.KafkaConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author wnhyang
 * @date 2024/2/29
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class EventESDataConsumer {

    @KafkaListener(topics = KafkaConstant.EVENT_ES_DATA, groupId = KafkaConstant.EVENT_ES_DATA_GROUP)
    public void consume(String message) {
        // TODO ES索引设计与存储
        log.info("consume message: {}", message);
        try {

        } catch (Exception e) {
            log.error("存储到ES失败", e);
        }

    }
}
