package com.pigeon.pmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pigeon.pmall.mapper.ReviewMapper;
import com.pigeon.pmall.pojo.Review;
import com.pigeon.pmall.pojo.ReviewExample;
import com.pigeon.pmall.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewMapper reviewMapper;
    
	@Override
	public void add(Review review) {
		reviewMapper.insert(review);
	}

	@Override
    public void delete(int id) {
		reviewMapper.deleteByPrimaryKey(id);
    }
	
	@Override
	public void update(Review review) {
		reviewMapper.updateByPrimaryKeySelective(review);
	}

	@Override
	public Review get(int id) {
		return reviewMapper.selectByPrimaryKey(id);
	}
	
    @Override
    public List<Review> list() {
    	ReviewExample example =new ReviewExample();
        example.setOrderByClause("id desc");
        return reviewMapper.selectByExample(example);
    }

	@Override
	public List<Review> listByPid(int pid) {
		ReviewExample example =new ReviewExample();
		example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");
        return reviewMapper.selectByExample(example);
	}
	
}
