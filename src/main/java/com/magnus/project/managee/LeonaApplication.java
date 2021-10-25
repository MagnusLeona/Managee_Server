package com.magnus.project.managee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class LeonaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeonaApplication.class, args);
	}

}
