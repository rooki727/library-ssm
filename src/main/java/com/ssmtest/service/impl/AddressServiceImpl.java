package com.ssmtest.service.impl;

import com.ssmtest.dao.AddressDao;
import com.ssmtest.entity.Address;
import com.ssmtest.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("addressService")
public class AddressServiceImpl implements AddressService {
@Autowired
private AddressDao addressDao;
    @Override
    public List<Address> getAddressListById(int user_id) {
        return addressDao.getAddressListById(user_id);
    }

    @Override
    public void deleteAddressById(int address_id) {
        addressDao.deleteAddressById(address_id);
    }

    @Override
    public void addAddress(Address address) {
        addressDao.addAddress(address);
    }

    @Override
    public Address getAddressById(int address_id) {
        return addressDao.getAddressById(address_id);
    }

    @Override
    public void updateAddress(Address address) {
         addressDao.updateAddress(address);
    }

    @Override
    public void updateAddressIsDefault(Address address) {
        addressDao.updateAddressIsDefault(address);
    }
}
