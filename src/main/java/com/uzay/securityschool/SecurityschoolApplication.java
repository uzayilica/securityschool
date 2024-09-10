package com.uzay.securityschool;

import com.uzay.securityschool.config.ConfigProperty;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@OpenAPIDefinition(
        info = @Info(
                title = "API Başlığı",
                version = "1.0.0",
                description = "API Açıklaması",
                contact = @Contact(
                        name = "İletişim Adı",
                        email = "email@example.com",
                        url = "http://example.com"
                ),
                license = @License(
                        name = "API Lisansı",
                        url = "http://example.com/license"
                )
        )
)
@EnableConfigurationProperties(ConfigProperty.class)
@EnableJpaAuditing(auditorAwareRef = "AwareConfig")
@SpringBootApplication
public class SecurityschoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityschoolApplication.class, args);
    }

}
