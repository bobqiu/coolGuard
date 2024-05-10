package cn.wnhyang.coolGuard.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author wnhyang
 * @date 2024/5/10
 **/
@Configuration
public class OkHttpClientConfig {

    @Value("${okhttp.connectTimeoutSeconds:10}")
    private int connectTimeoutSeconds;

    @Value("${okhttp.readTimeoutSeconds:10}")
    private int readTimeoutSeconds;

    @Value("${okhttp.writeTimeoutSeconds:10}")
    private int writeTimeoutSeconds;

    @Value("${okhttp.maxIdleConnections:10}")
    private int maxIdleConnections;

    @Value("${okhttp.idleConnectionTimeoutMinutes:5}")
    private int idleConnectionTimeoutMinutes;

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                // 设置连接超时时间
                .connectTimeout(connectTimeoutSeconds, TimeUnit.SECONDS)
                // 设置读取超时时间
                .readTimeout(readTimeoutSeconds, TimeUnit.SECONDS)
                // 设置写入超时时间
                .writeTimeout(writeTimeoutSeconds, TimeUnit.SECONDS)
                // 设置连接池最大空闲连接数和空闲连接的存活时间
                .connectionPool(new ConnectionPool(maxIdleConnections, idleConnectionTimeoutMinutes, TimeUnit.MINUTES))
                .build();
    }

}
