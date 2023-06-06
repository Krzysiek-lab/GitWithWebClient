package com.example.atipera.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class BeanCreation {
    @Bean
    public OpenAPI ServerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Git")
                        .description("Pobieranie repozytoriów github danego użytkownika"));
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}
