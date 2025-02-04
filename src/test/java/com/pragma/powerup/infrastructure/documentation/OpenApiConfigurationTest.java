package com.pragma.powerup.infrastructure.documentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenApiConfigurationTest {

    private final OpenApiConfiguration openApiConfiguration = new OpenApiConfiguration();

    @Test
    void testCustomOpenApi() {
        String description = "appDescription";
        String version = "appVersion";

        OpenAPI result = openApiConfiguration.customOpenApi(description, version);

        Assertions.assertEquals("3.0.1", result.getOpenapi(), "The OpenAPI version must be 3.0.1");

        Info info = result.getInfo();
        Assertions.assertNotNull(info, "Info must not be null");
        Assertions.assertEquals("Hexagonal Microservices Small Square  API", info.getTitle(), "The title does not match");
        Assertions.assertEquals(description, info.getDescription(), "The description does not match");
        Assertions.assertEquals("http://swagger.io/terms/", info.getTermsOfService(), "The terms of service do not match");
        Assertions.assertEquals(version, info.getVersion(), "The version does not match");

        License license = info.getLicense();
        Assertions.assertNotNull(license, "License must not be null");
        Assertions.assertEquals("Apache 2.0", license.getName(), "The name of the license does not match");
        Assertions.assertEquals("http://springdoc.org", license.getUrl(), "The URL of the license does not match");

        Components components = result.getComponents();
        Assertions.assertNotNull(components, "Components must not be null");
        Assertions.assertNotNull(components.getSecuritySchemes(), "SecuritySchemes must not be null");
        Assertions.assertTrue(components.getSecuritySchemes().containsKey("BearerAuth"),
                "The security scheme must contain the key BearerAuth");


        SecurityScheme securityScheme = components.getSecuritySchemes().get("BearerAuth");
        Assertions.assertNotNull(securityScheme, "The security scheme must not be null");

        Assertions.assertEquals("http", securityScheme.getType().toString(), "The type of the security scheme does not match");
        Assertions.assertEquals("bearer", securityScheme.getScheme(), "The scheme of the security scheme does not match");
        Assertions.assertEquals("JWT", securityScheme.getBearerFormat(), "The bearer format of the security scheme does not match");


        Assertions.assertNotNull(result.getSecurity(), "The security requirements must not be null");
        Assertions.assertFalse(result.getSecurity().isEmpty(), "The security requirements must not be empty");
    }
}
