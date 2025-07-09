package com.backend_project_template.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class InitLogger {

  private static final Logger logger = LoggerFactory.getLogger(InitLogger.class);
  private static final String[] ALLOWED_ENVIRONMENTS = { "development", "staging", "production" };

  private static final String RESET = "\u001B[0m";
  private static final String GREEN = "\u001B[32m";
  private static final String CYAN = "\u001B[36m";

  @Value("${spring.datasource.url}")
  private String datasourceUrl;

  @Value("${spring.datasource.username}")
  private String datasourceUsername;

  private void echoSeparator() {
    logger.info(CYAN + "--------------------------------------" + RESET);
  }

  private void echoGreen(String message) {
    logger.info(GREEN + message + RESET);
  }

  @EventListener
  public void logDataSourceConfig(ApplicationReadyEvent event) {
    logger.info("Current data source configuration:");
    logger.info("URL: {}", datasourceUrl);
    logger.info("Username: {}", datasourceUsername);
  }

  public void logCurrentEnvironment(ApplicationContext context) {
    String[] activeProfiles = context.getEnvironment().getActiveProfiles();
    String joinedProfiles = activeProfiles.length > 0 ? String.join(", ", activeProfiles) : "development (default)";

    ensureAllowedProfile(activeProfiles);

    echoSeparator();
    echoGreen("✨ Starting server with active profile(s): " + joinedProfiles);
    echoSeparator();
  }

  private void ensureAllowedProfile(String[] activeProfiles) {
    if (activeProfiles.length == 0) return;

    for (String profile : activeProfiles) {
      for (String allowed : ALLOWED_ENVIRONMENTS) {
        if (allowed.equals(profile)) {
          return;
        }
      }
    }

    throw new IllegalArgumentException("🚨 None of the active profiles are allowed: " + String.join(", ", activeProfiles));
  }
}
