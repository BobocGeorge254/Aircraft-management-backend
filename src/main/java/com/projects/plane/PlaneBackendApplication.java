package com.projects.plane;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Plane Management Application",
				version = "1.0.0",
				description = "Faculty project made to prove Spring Boot backend capabilities"
		)
)
public class PlaneBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaneBackendApplication.class, args);
	}

}
