package com.zyz.sparrow.mapper.first;

import com.zyz.sparrow.bean.Address;

public interface AddressMapper {
	/**
	 * 保存
	 */
	void save(Address address);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	Address get(Long id);
}
