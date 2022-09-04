package com.toss.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;

@Component
public class HttpEventPublisherAware implements ApplicationEventPublisherAware {
	private ApplicationEventPublisher publisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}

	public void sendRequest(String path) {
		ApplicationEvent event = new HttpEvent(this, path);
		publisher.publishEvent(event);
	}

	public void sendPayload(String path) {
		publisher.publishEvent(new Payload(path));
	}

	public void sendPayloadEvent(String path) {
		ApplicationEvent event = new PayloadApplicationEvent<>(this, new Payload(path));
		publisher.publishEvent(event);
	}
}
