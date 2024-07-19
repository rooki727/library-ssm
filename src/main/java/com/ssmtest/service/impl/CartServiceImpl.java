package com.ssmtest.service.impl;

import com.ssmtest.dao.CartDao;
import com.ssmtest.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cartService")
public class CartServiceImpl implements CartService{
    @Autowired
    private CartDao cartDao;
    @Override
    public int getCartCountById(int user_id) {
        return cartDao.getCartCountById(user_id);
    }
}
