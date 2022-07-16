package ar.edu.unlp.info.bd2.promocionbd2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "ar.edu.unlp.info.bd2.promocionbd2.mongoRepositories")
public class MongoConfiguration {
}

