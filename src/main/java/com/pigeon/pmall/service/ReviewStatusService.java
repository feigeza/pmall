package com.pigeon.pmall.service;

import java.util.List;

import com.pigeon.pmall.pojo.ReviewStatus;

public interface ReviewStatusService {
	
	String ESSENCE = "essence";
	String RECOMMEND = "recommend";
	String STICKY = "sticky";
	
	/**
     * 初始化商品对应的评价状态ReviewStatus
     * @param pid
     */
    void init(int rid);
    
    void update(ReviewStatus reviewStatus);
    
    /**
     * @param rid Review的id
     * @return
     */
    ReviewStatus get(int rid);
    
}
