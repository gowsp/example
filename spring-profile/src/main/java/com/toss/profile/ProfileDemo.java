package com.toss.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProfileDemo {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ProfileDemo.class, args);
		ConfigDemo config = context.getBean(ConfigDemo.class);
		System.out.println(config.getTitle());
		System.out.println(config.getPort());
		System.out.println(config.getName());
	}
}
