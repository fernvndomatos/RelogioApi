package com.github.fernvndomatos.RelogioApi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI relogioApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RelogioApi")
                        .description("API REST para controle de estoque de relógios")
                        .version("v1"));
    }
}