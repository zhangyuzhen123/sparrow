package com.zyz.sparrow.controller;

import com.zyz.sparrow.bean.User;
import com.zyz.sparrow.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/user/save")
	@ResponseBody
	public String save(@RequestBody User user) {
		userService.save(user);
		return "success";
	}
	
	@RequestMapping("/user/get/{id}")
	@ResponseBody
	public User get(@PathVariable Long id) {
		User user =  userService.get(id);
		System.out.println(user.getId());
		return user;
	}
	@RequestMapping("/user/find")
	@ResponseBody
	public List<Long> get() {
		List<Long> list =  userService.find();
		return list;
	}
}
