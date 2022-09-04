package com.toss.profile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class ConfigDemo {
	@Value("${title}")
	private String title;
	private int port;
	private String name;

	public String getTitle() {
		return title;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPort() {
		return port;
	}

	public String getName() {
		return name;
	}

}
