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
public class ConsumerService {
    @KafkaListener(topics = "test", groupId = "test-group")
    public void consume(String message) {
        log.info("consume message:{}", message);
    }
}
