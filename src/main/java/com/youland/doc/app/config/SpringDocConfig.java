package com.youland.doc.app.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearer-key",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .addServersItem(new Server().url("/docgen/"))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"))
                .info(new Info().title("Default")
                        .description("docgen service")
                        .version("v1.0")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("youland doc api")
                        .url("https://youland.com"));
    }

    @Bean
    public GroupedOpenApi defaultApi(){
        return GroupedOpenApi.builder()
                .group("Default")
                .packagesToScan("com.youland.doc.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi operationApi() {
        return GroupedOpenApi.builder()
                .group("Operation")
                .packagesToScan("org.springframework.boot.actuate")
                .build();
    }
}
