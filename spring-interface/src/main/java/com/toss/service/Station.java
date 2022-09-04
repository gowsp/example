package com.toss.service;

import com.toss.annotation.Proxy;

@Proxy
public interface Station {
	void sellTickets();

	void ticketChange();
}
