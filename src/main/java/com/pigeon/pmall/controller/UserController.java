package com.pigeon.pmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pigeon.pmall.pojo.User;
import com.pigeon.pmall.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/users")
	public PageInfo<User> list(@RequestParam(value="start", defaultValue="1")int start, @RequestParam(value="size", defaultValue="5")int size) throws Exception {
		
		PageHelper.startPage(start, size, "id desc");
		List<User> users = userService.list();
		PageInfo<User> page = new PageInfo<User> (users, 5);
		
		return page;
	}
	
	@GetMapping("/users/{id}")
	public User get(@PathVariable("id")int id) throws Exception {
		return userService.get(id);
	}
	
}
