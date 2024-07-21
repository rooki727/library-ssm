package com.ssmtest.dao;

import com.ssmtest.entity.Cart;
import com.ssmtest.entity.CartBook;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao {
   int getCartCountById(int user_id);
   List<CartBook> findBookCartByUserId(int user_id);
   void updateCartSelect(Cart cart);
   void updateCartNumber(Cart cart);
   void deleteMemberCart(int cart_id);
}
