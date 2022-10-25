package com.youland.doc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableAsync
public class DocApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocApplication.class, args);
    }

    @RestController
    public static class HeathCheckController {

        private static final String TEMPLATE = "%s v%s. Ping %d! %s";
        private final AtomicLong counter = new AtomicLong();

        @Value("${app.version}")
        private String appVersion;

        @Value("${app.name}")
        private String appName;

        @GetMapping
        public String ping() {
            return String.format(
                    TEMPLATE, this.appName, this.appVersion, counter.incrementAndGet(), Instant.now().toString());
        }
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("docgen-");
        executor.initialize();
        return executor;
    }
}
