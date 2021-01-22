package com.github.charlesluxinger.bytebank.controller.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@OpenAPIDefinition(
        info = @Info(
                title = "MS BYTE BANK API",
                version = "v1.0.0",
                description = "BYTE BANK API Challenge",
                contact = @Contact(name = "Charles Luxinger",
                        email = "charlesluxinger@gmail.com",
                        url = "https://github.com/CharlesLuxinger/bytebank-api")
        ),
        servers = @Server(url = "http://localhost:9000/api/v1", description = "Local Server")
)
public class OpenApiConfig {}
