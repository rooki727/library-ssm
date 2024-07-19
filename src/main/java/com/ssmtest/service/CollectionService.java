package com.ssmtest.service;

import com.ssmtest.entity.BookCollection;

public interface CollectionService {
    BookCollection getBookCollect(BookCollection bookCollection);
    void addCollection(BookCollection bookCollection);
    void deleteCollection(BookCollection bookCollection);
}
