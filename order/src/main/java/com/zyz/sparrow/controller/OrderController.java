package com.zyz.sparrow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {

	@GetMapping("/get/a")
	@ResponseBody
	public String getA() {
		return "aaaaaaaaaaaa";
	}
}
