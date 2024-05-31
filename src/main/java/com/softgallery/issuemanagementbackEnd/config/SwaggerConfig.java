package com.softgallery.issuemanagementbackEnd.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class    SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){

        SecurityScheme apiKey = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .scheme("bearer")
                .bearerFormat("JWT");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Token");
        return new OpenAPI()
                .info(new Info().title("Project Panda's API")
                        .description("Why is there a 'panda' in the name? There's a sad story there...\nWe don't get enough sleep and we have dark circles as a result.\nWe are becoming more and more like pandas.\nCheers.")
                        .version("v0.0.1"))
                .components(new Components().addSecuritySchemes("Bearer Token", apiKey))
                .addSecurityItem(securityRequirement);

    }
}
