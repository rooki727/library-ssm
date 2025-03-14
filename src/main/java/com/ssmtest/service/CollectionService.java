package com.ssmtest.service;

import com.ssmtest.entity.BookCollection;

import java.util.List;
import java.util.Map;

public interface CollectionService {
    BookCollection getBookCollect(BookCollection bookCollection);
    void addCollection(BookCollection bookCollection);
    void deleteCollection(BookCollection bookCollection);
    List<Map<String, Object>> getAllBookCollectCounts();
}
