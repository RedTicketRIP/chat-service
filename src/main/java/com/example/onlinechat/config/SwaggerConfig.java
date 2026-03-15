package com.example.onlinechat.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;


@SecurityScheme(
        type= SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        name="authorization"
)
@Configuration
public class SwaggerConfig {
}
