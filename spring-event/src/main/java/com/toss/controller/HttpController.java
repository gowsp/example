package com.toss.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import com.toss.event.HttpEvent;
import com.toss.event.Payload;

@Controller
public class HttpController {
	private final Log log = LogFactory.getLog(HttpController.class);

	@Async
	@EventListener(condition = "#a0.path=='/add'")
	public void add(HttpEvent httpEvent) {
		log.info("添加===" + httpEvent.getPath());
	}

	@EventListener(condition = "#a0.path=='/delete'")
	public void delete(HttpEvent httpEvent) {
		log.info("删除===" + httpEvent.getPath());
	}

	@Order(5)
	@EventListener(condition = "#a0.path=='/payload'")
	public void payload(Payload payload) {
		log.info("负载===" + payload.getPath());
	}

	@Order(10)
	@EventListener(condition = "#a0.payload.path=='/payloadEvent'")
	public void payloadEvent(PayloadApplicationEvent<Payload> payload) {
		log.info("负载事件===" + payload.getPayload().getPath());
	}
}
