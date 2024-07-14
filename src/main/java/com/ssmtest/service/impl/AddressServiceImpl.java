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
}
