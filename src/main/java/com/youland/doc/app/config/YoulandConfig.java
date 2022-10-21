package com.youland.doc.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: rico
 * @date: 2022/10/21
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "youland.service")
public class YoulandConfig{

    private String docsUrl;

    private final Test test = new Test();

    @Data
    public static class Test {
        private String test2;
    }
}
