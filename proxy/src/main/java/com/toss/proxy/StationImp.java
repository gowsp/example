package com.toss.proxy;

public class StationImp implements Station {

	@Override
	public void sellTickets() {
		System.out.println("售票");
	}

	@Override
	public void ticketChange() {
		System.out.println("改签");
	}

}
