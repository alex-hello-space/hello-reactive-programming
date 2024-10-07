package org.tutorial.alex.hello.reactive.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

/**
 * @author yyHuangfu
 * @create 2024/10/7
 */

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.exchangeStrategies(
                ExchangeStrategies.builder()
                        .codecs(it -> it.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                        .build()
        ).clientConnector(
                new ReactorClientHttpConnector(
                        HttpClient.create(ConnectionProvider.builder("reactive-demo")
                                .maxConnections(10)
                                .maxIdleTime(Duration.ofSeconds(20))
                                .maxLifeTime(Duration.ofSeconds(60))
                                .pendingAcquireTimeout(Duration.ofSeconds(60))
                                .evictInBackground(Duration.ofSeconds(120)).build())
                )
        ).build();
    }
}
