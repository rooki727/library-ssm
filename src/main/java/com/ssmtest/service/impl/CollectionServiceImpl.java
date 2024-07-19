package com.ssmtest.service.impl;

import com.ssmtest.dao.CollectionDao;
import com.ssmtest.entity.BookCollection;
import com.ssmtest.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("collectionService")
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionDao collectionDao;
    @Override
    public BookCollection getBookCollect(BookCollection bookCollection) {
        return collectionDao.getBookCollect(bookCollection);
    }

    @Override
    public void addCollection(BookCollection bookCollection) {
        collectionDao.addCollection(bookCollection);
    }

    @Override
    public void deleteCollection(BookCollection bookCollection) {
       collectionDao.deleteCollection(bookCollection);
    }
}
