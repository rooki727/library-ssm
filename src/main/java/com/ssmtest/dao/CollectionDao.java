package com.ssmtest.dao;

import com.ssmtest.entity.BookCollection;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionDao {
    BookCollection getBookCollect(BookCollection bookCollection);
    void addCollection(BookCollection bookCollection);
    void deleteCollection(BookCollection bookCollection);
}
