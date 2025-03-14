package cn.wnhyang.coolguard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * @author wnhyang
 * @date 2024/5/10
 **/
@Configuration
public class RestClientConfig {

    public void restClient() {
        RestClient restClient = RestClient.builder().baseUrl("https://api.github.com/").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

    }

}
