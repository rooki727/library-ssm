package com.ssmtest.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface CartDao {
   int getCartCountById(int user_id);
}
