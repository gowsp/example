package com.toss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.toss.service.Station;

@SpringBootApplication
public class AppLauncher {
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(AppLauncher.class, args);
		Station s = ctx.getBean(Station.class);
		s.sellTickets();
		s.ticketChange();
	}
}
