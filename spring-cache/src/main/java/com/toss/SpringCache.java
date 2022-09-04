package com.toss;

import com.toss.dao.IBookDao;
import com.toss.domain.Book;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

@EnableCaching
@SpringBootApplication
public class SpringCache {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SpringCache.class, args);
        IBookDao bookStore = ctx.getBean(IBookDao.class);
        Book book = new Book().setId(1L).setName("唐诗三百首");
        bookStore.storage(book);
        bookStore.view(1L);
        bookStore.view(1L);
        bookStore.sell(1L);
    }
}
