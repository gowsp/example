package com.toss.interceptor;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.support.RepositoryProxyPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomRepositoryProxyPostProcessor implements RepositoryProxyPostProcessor {
    @Override
    public void postProcess(ProxyFactory proxyFactory, RepositoryInformation repositoryInformation) {
        proxyFactory.addAdvice(new DynamicSqlMethodInterceptor(repositoryInformation));
    }
}
