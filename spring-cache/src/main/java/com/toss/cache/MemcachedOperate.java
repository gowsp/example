package com.toss.cache;

import net.rubyeye.xmemcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemcachedOperate {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MemcachedClient client;
    private int defaultTime = 120;

    /**
     * 设置缓存
     */
    public void setCache(String namespace, String key, Object value) {
        try {
            logger.info("存储缓存:" + value.toString());
            client.withNamespace(namespace, client -> client.set(key, defaultTime, value));
        } catch (Exception e) {
            logger.warn("缓存操作异常", e);
        }
    }

    /**
     * 获取缓存
     */
    public <T> T getCache(String namespace, String key) {
        try {
            T val = client.withNamespace(namespace, client -> client.get(key, 2));
            logger.info("获取缓存:{}", val);
            return val;
        } catch (Exception e) {
            logger.warn("缓存操作异常", e);
        }
        return null;
    }

    /**
     * 删除缓存
     */
    public void delete(String namespace, String key) {
        try {
            logger.info("删除缓存:" + key);
            client.withNamespace(namespace, client -> client.delete(key));
        } catch (Exception e) {
            logger.warn("缓存操作异常", e);
        }
    }

    /**
     * 失效缓存
     */
    public void clear(String namespace) {
        try {
            logger.info("失效缓存:" + namespace);
            client.invalidateNamespace(namespace);
        } catch (Exception e) {
            logger.warn("缓存操作异常", e);
        }
    }
}
