package com.moura.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("API REST para Cálculo de IMC")
                        .version("v1")
                        .description("API responsável pelo cadastro de usuários e cálculo do Índice de Massa Corporal (IMC)")
                        .contact(new Contact()
                                .name("Alisson de Moura")
                                .url("https://www.linkedin.com/in/alisson-moura-071410238/")
                                .email("alisson_moura_rj@hotmail.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório do projeto")
                        .url("https://github.com/moura92/Calculo_IMC"));
    }
}
