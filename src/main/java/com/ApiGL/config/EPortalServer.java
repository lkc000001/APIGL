package com.ApiGL.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "eportalloginpage")
public @Data class EPortalServer {
	
	private String url;
	
	private String apiUser;
	
	private String apiMima;
	
	private String apName;
}
