package com.zyz.sparrow.mapper.first;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyz.sparrow.bean.Address;

public interface AddressMapper extends BaseMapper {
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
