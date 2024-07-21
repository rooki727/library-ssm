package com.ssmtest.service;

import com.ssmtest.entity.Cart;
import com.ssmtest.entity.CartBook;

import java.util.List;

public interface CartService {
    int getCartCountById(int user_id);
    List<CartBook> findBookCartByUserId(int user_id);

    void updateCartSelect(Cart cart);
    void updateCartNumber(Cart cart);
    void deleteMemberCart(int cart_id);
}
