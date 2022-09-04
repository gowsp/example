package com.toss.cache;

import org.springframework.cache.support.AbstractValueAdaptingCache;

import java.util.concurrent.Callable;

public class MemcachedCache extends AbstractValueAdaptingCache {
    private final String name;
    private final MemcachedOperate operate;

    protected MemcachedCache(String name, MemcachedOperate operate) {
        super(false);
        this.name = name;
        this.operate = operate;
    }

    @Override
    protected Object lookup(Object key) {
        return operate.getCache(name, key.toString());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return operate;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        operate.setCache(name, key.toString(), value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        operate.setCache(name, key.toString(), value);
        return toValueWrapper(value);
    }

    @Override
    public void evict(Object key) {
        operate.delete(name, key.toString());
    }

    @Override
    public void clear() {
        operate.clear(name);
    }
}
