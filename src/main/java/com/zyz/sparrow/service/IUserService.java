package com.zyz.sparrow.service;

import com.zyz.sparrow.bean.User;

import java.util.List;

public interface IUserService {
	
	void save(User user);

	User get(Long l);

	List<Long> find();
}	
