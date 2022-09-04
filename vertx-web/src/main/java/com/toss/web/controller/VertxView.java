package com.toss.web.controller;

import org.springframework.stereotype.Controller;

import com.toss.web.common.IRoute;
import com.toss.web.common.RequestMapping;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

@Controller
public class VertxView implements IRoute {

	@RequestMapping(path = "/hello")
	public void hello(RoutingContext context) {
		HttpServerResponse response = context.response();
		response.end("Hello Vertx");
	}

	@RequestMapping(path = "/greet")
	public void greet(RoutingContext context) {
		HttpServerResponse response = context.response();
		response.end("Greet Vertx");
	}
}
