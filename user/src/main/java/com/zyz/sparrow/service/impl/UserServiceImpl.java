package com.zyz.sparrow.service.impl;

import java.util.Date;
import java.util.List;

import com.zyz.sparrow.bean.Address;
import com.zyz.sparrow.bean.User;
import com.zyz.sparrow.service.IUserService;
import com.zyz.sparrow.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyz.sparrow.mapper.second.UserMapper;


@Service
public class UserServiceImpl implements IUserService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IAddressService addressService;
    @Override
    public void save(User user) {
        user.setCreateTime(new Date());
        userMapper.save(user);
        Address address = new Address();
        address.setCode(user.getName());
        addressService.save(address);
    }

    @Override
    public User get(Long id) {
        // TODO Auto-generated method stub
        return userMapper.get(id);
    }


    @Override
    public List<Long> find() {
        return userMapper.find();
    }

}
