package com.backend_project_template.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {

  @Bean
  CommandLineRunner init() {
    return args -> {};
  }
}
