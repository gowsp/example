package com.toss.interceptor;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.jdbc.repository.support.JdbcRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.core.support.RepositoryProxyPostProcessor;

import java.io.Serializable;

/**
 * Spring Data JDBC工厂类
 *
 * @author hunter
 */
public class CustomRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable>
        extends JdbcRepositoryFactoryBean {
    private BeanFactory beanFactory;

    protected CustomRepositoryFactoryBean(Class repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        this.beanFactory = beanFactory;
    }

    @Override
    protected RepositoryFactorySupport doCreateRepositoryFactory() {
        RepositoryFactorySupport repositoryFactorySupport = super.doCreateRepositoryFactory();
        RepositoryProxyPostProcessor postProcessor = beanFactory.getBean(CustomRepositoryProxyPostProcessor.class);
        repositoryFactorySupport.addRepositoryProxyPostProcessor(postProcessor);
        return repositoryFactorySupport;
    }
}