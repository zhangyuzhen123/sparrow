package com.zyz.sparrow.controller;

import com.zyz.sparrow.bean.Address;
import com.zyz.sparrow.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AddressController {

	@Autowired
	private IAddressService addressService;

	@RequestMapping("/address/save")
	@ResponseBody
	public String save(Address address) {
		addressService.save(address);
		return "success";
	}

	@RequestMapping("/address/get/{id}")
	@ResponseBody
	public Address get(@PathVariable Long id) {
		return addressService.get(id);
	}
}
