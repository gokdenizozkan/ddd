package com.gokdenizozkan.ddd.recommendationservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "DDD - Recommendation Service API",
                version = "1.0",
                description = "Recommendation Service API Documentation",
                license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT"),
                contact = @Contact(
                        name = "Gokdeniz Ozkan",
                        email = "gokdeniz@gokdenizozkan.com",
                        url = "https://github.com/gokdenizozkan")
        )
)
public class OpenApiConfig {
}
