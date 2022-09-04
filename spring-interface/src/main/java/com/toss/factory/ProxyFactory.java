package com.toss.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;

public class ProxyFactory<T> implements FactoryBean<T> {
	private Class<T> proxyInterface;

	public ProxyFactory() {
		super();
	}

	public ProxyFactory(Class<T> proxyInterface) {
		super();
		this.proxyInterface = proxyInterface;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getObject() throws Exception {
		InvocationHandler h = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("接口类:" + method.getDeclaringClass().getName());
				System.out.println("接口方法:" + method.getName());
				return null;
			}
		};
		Class<?>[] interfaces = new Class<?>[] { proxyInterface };
		return (T) Proxy.newProxyInstance(getClass().getClassLoader(), interfaces, h);
	}

	@Override
	public Class<?> getObjectType() {
		return proxyInterface;
	}

}
