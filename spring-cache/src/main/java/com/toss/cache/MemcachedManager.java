package com.toss.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component("cacheManager")
public class MemcachedManager extends AbstractCacheManager {
    @Autowired
    private MemcachedOperate operate;

    @Override
    protected Cache getMissingCache(String name) {
        return new MemcachedCache(name, operate);
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return Collections.emptyList();
    }
}
