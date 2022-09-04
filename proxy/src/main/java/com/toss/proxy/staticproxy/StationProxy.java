package com.toss.proxy.staticproxy;

import com.toss.proxy.Station;
import com.toss.proxy.StationImp;

public class StationProxy implements Station {
	private Station target;

	public StationProxy(Station station) {
		this.target = station;
	}

	@Override
	public void sellTickets() {
		System.out.println("售票手续费");
		target.sellTickets();
	}

	@Override
	public void ticketChange() {
		System.out.println("改签手续费");
		target.ticketChange();
	}

	public static void main(String[] args) {
		StationProxy proxy = new StationProxy(new StationImp());
		proxy.sellTickets();
		proxy.ticketChange();
	}

}
