package br.com.fiap.nextia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
public class SwaggerConfig {

    @Bean(name = "customSwaggerConfig")
    public OpenAPI swaggerConfig() {
        SecurityScheme securityScheme = new SecurityScheme()
                .name("basicAuth")
                .type(Type.HTTP)
                .scheme("basic");

        return new OpenAPI()
                .info(new Info()
                        .title("API da NextIA"))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                .components(new io.swagger.v3.oas.models.Components().addSecuritySchemes("basicAuth", securityScheme));
    }
}
