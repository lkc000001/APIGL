package com.ApiGL.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "rootlogin")
public @Data class RootLogin {
	
	private String user;
	
	private String mima;
}
