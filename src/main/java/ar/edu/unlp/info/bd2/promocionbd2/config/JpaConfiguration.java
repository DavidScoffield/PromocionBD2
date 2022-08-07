package ar.edu.unlp.info.bd2.promocionbd2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "ar.edu.unlp.info.bd2.promocionbd2.repositories")
public class JpaConfiguration {
}