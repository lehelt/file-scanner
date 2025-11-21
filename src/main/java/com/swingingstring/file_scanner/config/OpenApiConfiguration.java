package com.swingingstring.file_scanner.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

/**
 * Global OpenAPI metadata for the File Scanner service.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "File Scanner API",
                version = "v1",
                description = "API for recursively scanning directories and retrieving scan history.",
                contact = @Contact(name = "File Scanner", email = "noreply@example.com"),
                license = @License(name = "MIT")
        )
)
public class OpenApiConfiguration {
}







