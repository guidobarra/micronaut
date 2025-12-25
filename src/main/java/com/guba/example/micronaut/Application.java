package com.guba.example.micronaut;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(
        info = @Info(
                title = "${app.openapi.title}",
                version = "${app.openapi.version}",
                description = "${app.openapi.description}",
                license = @License(name = "${app.openapi.license}", url = "https://opensource.org/licenses/MIT"),
                contact = @Contact(
                        name = "${app.openapi.contact.name}",
                        email = "${app.openapi.contact.email}",
                        url = "${app.openapi.contact.url}"
                )
        )
)
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOG.info("Starting Micronaut application...");
        Micronaut.run(Application.class, args);
        LOG.info("Micronaut application started successfully");
    }
}

