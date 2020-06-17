package com.zyz.sparrow.service.impl;

import java.util.Date;
import java.util.List;

import com.zyz.sparrow.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyz.sparrow.bean.User;
import com.zyz.sparrow.mapper.second.UserMapper;


@Service
public class UserServiceImpl implements IUserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public void save(User user) {
        user.setCreateTime(new Date());
        userMapper.save(user);
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
