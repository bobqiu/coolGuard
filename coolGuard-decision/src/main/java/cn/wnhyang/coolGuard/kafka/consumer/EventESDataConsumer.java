package cn.wnhyang.coolGuard.kafka.consumer;

import cn.wnhyang.coolGuard.constant.KafkaConstant;
import cn.wnhyang.coolGuard.es.ElasticSearchService;
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

    private final ElasticSearchService elasticSearchService;

    @KafkaListener(topics = KafkaConstant.EVENT_ES_DATA, groupId = KafkaConstant.EVENT_ES_DATA_GROUP)
    public void consume(String message) {
        // TODO 存储到ES
        log.info("consume message: {}", message);
        try {
            elasticSearchService.indexDocument("event_20241219", message);
        } catch (Exception e) {
            log.error("存储到ES失败", e);
        }

    }
}
