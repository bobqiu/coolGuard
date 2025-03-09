package cn.wnhyang.coolGuard.decision.es;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author wnhyang
 * @date 2024/12/19
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class ElasticSearchService {

    private final RestHighLevelClient restHighLevelClient;

    public void indexDocument(String indexName, String json) throws IOException {
        IndexRequest request = new IndexRequest(indexName).source(json, XContentType.JSON);
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        log.info("Indexed document with ID: " + response.getId());
    }
}
