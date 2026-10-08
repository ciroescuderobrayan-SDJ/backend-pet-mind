package com.cesde.petmind.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "PetMind API",
        version = "1.0.0",
        description = "API para la plataforma de adopción de mascotas PetMind.",
        contact = @Contact(
            name = "CESDE",
            email = "support@petmind.com"
        ),
        license = @License(
            name = "MIT",
            url = "https://opensource.org/licenses/MIT"
        )
    )
)
public class OpenApiConfig {
}
