package com.toss.event;

import org.springframework.context.ApplicationEvent;

public class HttpEvent extends ApplicationEvent {

	private static final long serialVersionUID = 2297911956451456760L;
	private String path;

	public HttpEvent(Object source, String path) {
		super(source);
		this.path = path;
	}

	public String getPath() {
		return path;
	}

}
