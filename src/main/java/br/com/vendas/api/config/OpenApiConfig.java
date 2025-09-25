package br.com.vendas.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("API de Vendas")
            .description(
                "API REST para gerenciamento de vendas desenvolvida em Java 21 com Spring Boot")
            .version("1.0.0")
            .contact(new Contact()
                .name("João Victor")
                .email("joaomunhoz.dev@gmail.com")
            ));
  }
}

