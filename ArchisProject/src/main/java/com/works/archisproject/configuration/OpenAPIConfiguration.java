package com.works.archisproject.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Archi's Academy Project Development");

        Contact myContact = new Contact();
        myContact.setName("Adil ALTUN");
        myContact.setEmail("adilaltun08@gmail.com");

        License license = new License();
        license.name("Apache 2.0");

        ExternalDocumentation externalDocumentation = new ExternalDocumentation();
        externalDocumentation.description("Spring Boot Project");

        Info information = new Info()
                .title("Archi's Academy Project Development System API")
                .version("1.0")
                .description("Rest API Documentation")
                .contact(myContact)
                .license(license);

        return new OpenAPI().info(information).externalDocs(externalDocumentation).servers(List.of(server));
    }

}
