package ar.edu.unlp.info.bd2.promocionbd2.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "ar.edu.unlp.info.bd2.promocionbd2.elasticsearchRepositories")
@ComponentScan(basePackages = {"ar.edu.unlp.info.bd2.promocionbd2"})
public class ElasticsearchConfiguration {
  
}