package com.ssmtest.dao;

import com.ssmtest.entity.BookCollection;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CollectionDao {
    BookCollection getBookCollect(BookCollection bookCollection);
    void addCollection(BookCollection bookCollection);
    void deleteCollection(BookCollection bookCollection);
    List<Map<String, Object>> getAllBookCollectCounts();
}
