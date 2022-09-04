package com.toss.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;

@Configuration
public class VertxConfig {

	@Bean
	public Vertx vertx() {
		return Vertx.vertx();
	}

	@Bean
	public Router router(Vertx vertx) {
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		router.route().handler(CookieHandler.create());
		SessionStore store = LocalSessionStore.create(vertx);
		router.route().handler(SessionHandler.create(store));
		return router;
	}

}
