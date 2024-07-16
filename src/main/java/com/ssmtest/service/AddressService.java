package com.ssmtest.service;

import com.ssmtest.entity.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAddressListById(int user_id);
    void deleteAddressById(int address_id);
    void addAddress(Address address);
    Address getAddressById(int address_id);
    void updateAddress(Address address);
    void updateAddressIsDefault(Address address);
}
