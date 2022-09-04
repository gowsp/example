package com.toss;

import net.rubyeye.xmemcached.utils.XMemcachedClientFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class BranConfig {
    @Bean
    XMemcachedClientFactoryBean xMemcachedClientFactoryBean() {
        XMemcachedClientFactoryBean factoryBean = new XMemcachedClientFactoryBean();
        factoryBean.setServers("192.168.1.200:11211");
        return factoryBean;
    }
}
