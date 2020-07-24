package com.zyz.sparrow.service;

import com.zyz.sparrow.bean.Address;

public interface IAddressService {
	void save(Address address);

	Address get(Long id);
}
