package com.exostar.assesment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenApiConfig is a configuration class that sets up the OpenAPI specification for the application.
 * It provides a custom OpenAPI bean that is used to generate the API documentation.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Creates a custom OpenAPI bean with the application's information.
     * The information includes the title, description, and version of the application.
     * @return An OpenAPI object with the application's information.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Management API")
                        .description("This is a sample User Management service using springdoc-openapi and Spring Boot")
                        .version("1.0.0"));
    }
}