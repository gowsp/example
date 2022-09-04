package com.toss;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.toss.event.HttpEventPublisherAware;

@EnableAsync
@ComponentScan(basePackages = "com.toss")
public class Application {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
		HttpEventPublisherAware publisherAware = context.getBean(HttpEventPublisherAware.class);
		publisherAware.sendRequest("/add");
		publisherAware.sendRequest("/delete");
		publisherAware.sendRequest("/modify");
		publisherAware.sendPayload("/payload");
		publisherAware.sendPayloadEvent("/payloadEvent");
	}

	@Bean
	public TaskExecutor taskExecutor() {
		TaskExecutor taskExecutor = new ThreadPoolTaskScheduler();
		return taskExecutor;
	}
}
