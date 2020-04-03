package com.pigeon.pmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pigeon.pmall.pojo.Review;
import com.pigeon.pmall.pojo.ReviewStatus;
import com.pigeon.pmall.service.ReviewStatusService;
import com.pigeon.pmall.util.Result;

@RestController
public class ReviewStatusController {
	
	@Autowired
	ReviewStatusService reviewStatusService;
	
	@GetMapping("/reviewStatus/{rid}")
	public ReviewStatus get(@PathVariable("rid") int rid) throws Exception {
		reviewStatusService.init(rid);
		return reviewStatusService.get(rid);
	}
	
	@PutMapping("/reviewStatus")
	public Object update(@RequestBody Review bean, String str) throws Exception {
		
		ReviewStatus rs = reviewStatusService.get(bean.getId());
		
		switch (str) {
		case ReviewStatusService.ESSENCE:
			rs.setEssence(rs.getEssence()==0?1:0);
			break;
		case ReviewStatusService.RECOMMEND:
			rs.setRecommend(rs.getRecommend()==0?1:0);
			break;
		case ReviewStatusService.STICKY:
			rs.setSticky(rs.getSticky()==0?1:0);
			break;
		default:
			return Result.fail("参数错误");
		}
		
		reviewStatusService.update(rs);
		
		return bean;
	}
	
}
