package com.example.apiestudo;

import com.example.apiestudo.config.AdminProperties;
import com.example.apiestudo.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({JwtProperties.class, AdminProperties.class})
@SpringBootApplication
public class ApiestudoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiestudoApplication.class, args);
	}

}
