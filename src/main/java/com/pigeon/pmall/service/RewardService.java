package com.pigeon.pmall.service;

import java.util.List;

import com.pigeon.pmall.pojo.Reward;

public interface RewardService {
	
	String ADD = "add"; //增加积分
	String USE = "use"; //减少积分
	//String FIRST = "first";
	
	void add(Reward reward);
	
	/**
	 * 获取用户对应的积分
	 * @param uid
	 * @return
	 */
	List<Reward> list(int uid);
	
}
