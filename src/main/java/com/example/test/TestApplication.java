package com.example.test;

import com.example.test.actuator.TacoMetrics;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(TestApplication.class);
		springApplication.addListeners(new TacoMetrics());
		springApplication.run(args);
	}

}
