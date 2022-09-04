package com.toss.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.toss.annotation.Proxy;
import com.toss.factory.ProxyFactory;

public class ClassPathProxyScanner extends ClassPathBeanDefinitionScanner {

	public ClassPathProxyScanner(BeanDefinitionRegistry registry) {
		super(registry, false);
	}

	@Override
	protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
		GenericBeanDefinition definition;
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
		for (BeanDefinitionHolder holder : beanDefinitions) {
			definition = (GenericBeanDefinition) holder.getBeanDefinition();
			definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName());
			definition.setBeanClass(ProxyFactory.class);
		}
		return beanDefinitions;
	}

	protected void registerFilters() {
		addIncludeFilter(new AnnotationTypeFilter(Proxy.class));
	}

	@Override
	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return beanDefinition.getMetadata().isInterface();
	}
}
