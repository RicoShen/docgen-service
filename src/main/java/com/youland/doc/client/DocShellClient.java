package com.youland.doc.client;

import com.youland.doc.client.config.DocShellSetting;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author: rico
 * @date: 2022/10/21
 **/
@Data
@Configuration
@Component
public class DocShellClient {

    private final ClientBaseSetting clientBaseSetting;
    private final WebClient webClient;

    public DocShellClient(ClientBaseSetting clientBaseSetting) {
        this.clientBaseSetting = clientBaseSetting;

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer
                        -> configurer.defaultCodecs().maxInMemorySize(clientBaseSetting.getMaxMemSizeByte()))
                .build();

        this.webClient = WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .baseUrl(clientBaseSetting.getUrlBase())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public boolean convertWordToPdf(String fileName){

       return webClient.post()
                .uri("/convert/wordToPdf/{fileName}",fileName)
                .bodyValue(BodyInserters.empty())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block(clientBaseSetting.getQueryTimeout());
    }

}
