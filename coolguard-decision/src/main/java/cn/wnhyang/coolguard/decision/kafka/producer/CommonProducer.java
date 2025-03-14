package cn.wnhyang.coolguard.decision.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @author wnhyang
 * @date 2024/2/28
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, String message) {
        CompletableFuture<SendResult<String, Object>> send = kafkaTemplate.send(topic, message);
        send.whenComplete((result, exception) -> {
            if (exception != null) {
                log.warn("send message error", exception);
            } else {
                log.info("send message success, topic:{}, message:{}", topic, message);
            }
        });
    }

}
