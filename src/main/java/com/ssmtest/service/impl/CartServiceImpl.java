package com.ssmtest.service.impl;

import com.ssmtest.dao.CartDao;
import com.ssmtest.entity.Cart;
import com.ssmtest.entity.CartBook;
import com.ssmtest.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService{
    @Autowired
    private CartDao cartDao;
    @Override
    public int getCartCountById(int user_id) {
        return cartDao.getCartCountById(user_id);
    }

    @Override
    public List<CartBook> findBookCartByUserId(int user_id) {
        return cartDao.findBookCartByUserId(user_id);
    }

    @Override
    public void updateCartSelect(Cart cart) {
        cartDao.updateCartSelect(cart);
    }
    @Override
    public void updateCartNumber(Cart cart) {
        cartDao.updateCartNumber(cart);
    }

    @Override
    public void deleteMemberCart(int cart_id) {
        cartDao.deleteMemberCart(cart_id);
    }
}
