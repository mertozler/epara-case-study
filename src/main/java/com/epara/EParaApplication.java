package com.epara;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@SpringBootApplication
public class EParaApplication {
	@Bean
	public OpenAPI customOpenAPI(@Value("${application-description}") String description,
								 @Value("${application-version}") String version){
		return new OpenAPI()
				.info(new Info()
						.title("E-Para API")
						.version(version)
						.description(description)
						.license(new License().name("E-Para API Licence")));
	}
	public static void main(String[] args) {
		SpringApplication.run(EParaApplication.class, args);
	}

}
