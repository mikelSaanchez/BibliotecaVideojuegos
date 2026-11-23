package com.dawes.bibliotecavideojuegos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info()
                .title("API REST GameVault")
                .version("2.0.0")
                .description("API REST de la biblioteca de videojuegos GameVault.")
                .contact(
                        new Contact()
                                .name("Mikel")
                                .email("mklsanchezromero@gmail.com")
                )
        );
    }
}
