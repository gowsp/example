package com.toss.proxy.dynamic.bytebuddy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.toss.proxy.Station;
import com.toss.proxy.StationImp;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.matcher.ElementMatchers;

public class BytebuddyProxy {
	private Station target;

	public BytebuddyProxy(Station target) {
		this.target = target;
	}

	public Object intercept(@AllArguments Object[] args, @Origin Method method)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("手续费");
		return method.invoke(target, args);
	}

	public static void main(String[] args) {
		Class<? extends StationImp> clazz = new ByteBuddy().subclass(StationImp.class)
				.method(ElementMatchers.nameContainsIgnoreCase("ticket"))
				.intercept(MethodDelegation.to(new BytebuddyProxy(new StationImp()))).make()
				.load(StationImp.class.getClassLoader()).getLoaded();
		try {
			Station object = clazz.newInstance();
			object.sellTickets();
			object.ticketChange();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
