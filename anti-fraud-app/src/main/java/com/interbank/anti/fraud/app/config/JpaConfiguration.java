package com.interbank.anti.fraud.app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.interbank.anti.fraud.core.repository")
@EntityScan("com.interbank.anti.fraud")
public class JpaConfiguration {
}
