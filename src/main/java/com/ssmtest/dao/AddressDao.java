package com.ssmtest.dao;

import com.ssmtest.entity.Address;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AddressDao {
    List<Address> getAddressListById(int user_id);
    void deleteAddressById(int address_id);

    void addAddress(Address address);

    Address getAddressById(int address_id);
    void updateAddress(Address address);
    void updateAddressIsDefault(Address address);
}
