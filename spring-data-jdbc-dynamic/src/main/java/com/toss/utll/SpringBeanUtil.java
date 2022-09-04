package com.toss.utll;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.util.Lazy;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class SpringBeanUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        synchronized (this) {
            if (SpringBeanUtil.applicationContext == null) {
                SpringBeanUtil.applicationContext = applicationContext;
            }
        }
    }

    /**
     * 获取Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 获取延迟Bean
     */
    public static <T> Supplier<T> getDelayedBean(Class<T> clazz) {
        return Lazy.of(() -> getBean(clazz));
    }
}