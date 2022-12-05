package com.ApiGL;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@EnableJdbcRepositories("com.ApiGL.repositories")
@Configuration
public class ApplicationConfig extends AbstractJdbcConfiguration {
	  
}
