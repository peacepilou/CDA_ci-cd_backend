package com.backend_project_template;

import com.backend_project_template.config.InitLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {

  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(Main.class, args);
    InitLogger logger = new InitLogger();
    logger.logCurrentEnvironment(context);
  }
}
