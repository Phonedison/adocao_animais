package org.serratec.adocao_pets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {

        Contact contato = new Contact();
        contato.setEmail("lucasleal00100@gmail.com");
        contato.setName("Lucas da Silva");
        contato.setUrl("https://github.com/Phonedison");

        /* LICENÇA */
        License apacheLicense = new License()
                .name("Apache License")
                .url("https://apache.org./licenses/LICENSE-2.0");

        /* MAIS INFO DA API */
        Info info = new Info()
                .title("Trabalho Individual")
                .version("0.0")
                .contact(contato)
                // .description("Api blaba")
                // .termsOfService("link")
                .license(apacheLicense);

        return new OpenAPI().info(info);
    }
}
