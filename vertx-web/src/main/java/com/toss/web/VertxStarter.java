package com.toss.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

@Component
public class VertxStarter implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private Vertx vertx;
	@Autowired
	private List<Verticle> verticles;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		verticles.forEach(verticle -> {
			vertx.deployVerticle(verticle);
		});
	}
}
