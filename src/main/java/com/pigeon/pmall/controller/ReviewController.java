package com.pigeon.pmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pigeon.pmall.pojo.Review;
import com.pigeon.pmall.service.ReviewService;
import com.pigeon.pmall.service.ReviewStatusService;

@RestController
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	@Autowired
	ReviewStatusService reviewStatusService;
	
	@GetMapping("products/{pid}/reviews")
	public PageInfo<Review> list(@PathVariable int pid, @RequestParam(value="start", defaultValue="1")int start, @RequestParam(value="size", defaultValue="5")int size) throws Exception {
		
		PageHelper.offsetPage(start, size, "id desc");
		List<Review> reviews = reviewService.listByPid(pid);
		PageInfo<Review> page = new PageInfo<Review>(reviews, 5);
		
		return page;
	}

}
