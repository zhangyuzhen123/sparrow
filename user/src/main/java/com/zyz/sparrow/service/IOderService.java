package com.zyz.sparrow.service;

import com.zyz.sparrow.bean.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "order")
public interface IOderService {
	@GetMapping("/get/a")
	String getA();

}	
