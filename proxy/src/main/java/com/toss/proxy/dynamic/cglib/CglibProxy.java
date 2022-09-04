package com.toss.proxy.dynamic.cglib;

import java.lang.reflect.Method;

import com.toss.proxy.Station;
import com.toss.proxy.StationImp;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {
	private Station target;

	public CglibProxy(Station target) {
		this.target = target;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("手续费");
		return method.invoke(target, args);
	}

	public static void main(String[] args) {
		Station station = (Station) Enhancer.create(StationImp.class, new CglibProxy(new StationImp()));
		station.sellTickets();
		station.ticketChange();
	}

}
