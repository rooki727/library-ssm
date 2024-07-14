package com.ssmtest.dao;

import com.ssmtest.entity.Address;

import java.util.List;

public interface AddressDao {
    List<Address> getAddressListById(int user_id);
}
