package cl.proyectum.product.neg.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title       = "API de Gestión de Productos",
                version     = "1.0.0",
                description = "Documentación de los endpoints para gestionar Productos",
                contact     = @Contact(name = "Soporte proyectum", email = "soporte@proyectum.cl", url = "https://proyectum.cl")
        ),
        servers = {
                @Server(url = "/api-product-proyectum", description = "Producción"),
                @Server(url = "/api-product-proyectum", description = "Local")
        },
        security = {@SecurityRequirement(name = "bearerAuth")}
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
}
