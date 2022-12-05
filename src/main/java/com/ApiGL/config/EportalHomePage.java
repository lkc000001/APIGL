package com.ApiGL.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "eportalhomepage")
public @Data class EportalHomePage {

	private String homepage;
		
}
