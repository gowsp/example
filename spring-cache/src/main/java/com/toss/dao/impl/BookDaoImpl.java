package com.toss.dao.impl;

import com.toss.dao.IBookDao;
import com.toss.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Repository
public class BookDaoImpl implements IBookDao {
    private Map<Long, Book> bookMap = new HashMap<>();
    @Override

    public void storage(Book book) {
        bookMap.put(book.getId(), book);
    }

    @Override
    public Book view(long l) {
        return bookMap.get(l);
    }

    @Override
    public void sell(long l) {
        bookMap.remove(l);
    }
}
