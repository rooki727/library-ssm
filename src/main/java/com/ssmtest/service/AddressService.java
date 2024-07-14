package com.ssmtest.service;

import com.ssmtest.entity.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAddressListById(int user_id);
}
