package com.toss.proxy.dynamic.jdk;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.toss.proxy.Station;
import com.toss.proxy.StationImp;

public class JDKProxy implements InvocationHandler {
	private Station target;

	public JDKProxy(Station target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("手续费");
		return method.invoke(target, args);
	}

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		Class<?> proxyClass = Proxy.getProxyClass(Station.class.getClassLoader(), Station.class);
		try {
			Constructor<?> constructor = proxyClass.getConstructor(InvocationHandler.class);
			Station station = (Station) constructor.newInstance(new JDKProxy(new StationImp()));
			station.sellTickets();
			station.ticketChange();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
