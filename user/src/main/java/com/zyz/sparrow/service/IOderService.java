package com.zyz.sparrow.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "order")
public interface IOderService {
	@GetMapping("/get/a")
	String getA();

}	
