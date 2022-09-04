package com.toss.dao;

import com.toss.domain.Book;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 *
 */
@CacheConfig(cacheNames = "book")
public interface IBookDao {
    @CacheEvict(allEntries = true)
    void storage(Book book);

    @Cacheable
    Book view(long l);

    @CacheEvict
    void sell(long l);
}
