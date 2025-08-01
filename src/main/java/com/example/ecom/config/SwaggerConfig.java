package com.example.ecom.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
  @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI()
                .info(new Info()
                    .title("Ecommerce API")
                    .version("1.0")
                    .description("Detailed API documentation with Swagger and Springdoc"));
        openAPI.setServers(Arrays.asList(
            new Server().url("http://localhost:8080")
                .description("Development Server"),
            new Server().url("https://ecommerce-spring-i3hj.onrender.com")
                .description("Render Deployment")));
        openAPI.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"));
        openAPI.components(new Components()
            .addSchemas("ApiResponse", createApiResponseSchema())
            .addSchemas("ErrorResponse", createErrorResponseSchema()));
        return openAPI;
    }

    private Schema<?> createApiResponseSchema() {
        return new Schema<>()
            .type("object")
            .addProperty("success",
                new Schema<>().type("boolean").description("Operation success status"))
            .addProperty("message", new Schema<>().type("string").description("Response message"))
            .addProperty("data", new Schema<>().description("Response data"))
            .addProperty("timestamp", new Schema<>().type("string").format("date-time")
                .description("Response timestamp"));
    }

    private Schema<?> createErrorResponseSchema() {
        return new Schema<>()
            .type("object")
            .addProperty("timestamp", new Schema<>().type("string").format("date-time"))
            .addProperty("status", new Schema<>().type("integer"))
            .addProperty("error", new Schema<>().type("string"))
            .addProperty("message", new Schema<>().type("string"))
            .addProperty("path", new Schema<>().type("string"));
    }
}
