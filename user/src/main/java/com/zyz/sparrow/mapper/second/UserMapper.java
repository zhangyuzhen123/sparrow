package com.zyz.sparrow.mapper.second;

import com.zyz.sparrow.bean.User;

import java.util.List;

public interface UserMapper {
	/**
	 * 保存
	 */
	void save(User user);
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	User get(Long id);

	/**
	 * 分页
	 * @return
	 */
	List<Long> find();
}
