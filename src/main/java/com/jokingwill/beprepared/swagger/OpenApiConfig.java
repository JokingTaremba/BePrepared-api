package com.jokingwill.beprepared.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "BePrepared",
                description = "O app permite que os usuários se mantenham informados e seguros durante desastres naturais",
                version = "1.0",
                contact = @Contact(
                        name = "Joking Will Gabriel",
                        email = "jokingtaremba@gmail.com",
                        url = "https://github.com/JokingTaremba"
                ),
                license = @License(
                        name = "",
                        url = ""
                )
        ),
        security = {
                @SecurityRequirement(
                        name = "Bearer Authentication"
                )
        }
)
@SecurityScheme(
        name = "Bearer Authentication",
        description = "Faça login na API, para poder usar perfeitamente a aplicação BePrepared," +
                "Coloque seu token de autenticação no campo a baixo e clique no botão Authorize",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
