package com.pigeon.pmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pigeon.pmall.mapper.ReviewStatusMapper;
import com.pigeon.pmall.pojo.Review;
import com.pigeon.pmall.pojo.ReviewStatus;
import com.pigeon.pmall.pojo.ReviewStatusExample;
import com.pigeon.pmall.service.ReviewService;
import com.pigeon.pmall.service.ReviewStatusService;

@Service
public class ReviewStatusServiceImpl implements ReviewStatusService {

	@Autowired
	ReviewStatusMapper reviewStatusMapper;
	@Autowired
	ReviewService reviewService;
	
	@Override
	public void init(int rid) {
		ReviewStatus rs = get(rid);
		if(null==rs) {
			ReviewStatus reviewStatus = new ReviewStatus();
			reviewStatus.setRid(rid);
			reviewStatus.setEssence(0);
			reviewStatus.setRecommend(0);
			reviewStatus.setSticky(0);
			reviewStatusMapper.insert(reviewStatus);
		}
	}
	
	@Override
	public void update(ReviewStatus reviewStatus) {
		reviewStatusMapper.updateByPrimaryKeySelective(reviewStatus);
	}

	@Override
	public ReviewStatus get(int rid) {
		ReviewStatusExample example = new ReviewStatusExample();
		example.createCriteria().andRidEqualTo(rid);
		example.setOrderByClause("id desc");
		List<ReviewStatus> result = reviewStatusMapper.selectByExample(example);
		return result.isEmpty()?null:result.get(0);
	}

}
