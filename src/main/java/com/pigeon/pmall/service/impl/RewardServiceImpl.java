package com.pigeon.pmall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pigeon.pmall.mapper.RewardMapper;
import com.pigeon.pmall.pojo.Reward;
import com.pigeon.pmall.pojo.RewardExample;
import com.pigeon.pmall.service.RewardService;

@Service
public class RewardServiceImpl implements RewardService {

	@Autowired
	RewardMapper rewardMapper;
	
	@Override
	public void add(Reward reward) {
		rewardMapper.insert(reward);
	}
	
	@Override
	public List<Reward> list(int uid) {
		RewardExample example = new RewardExample();
		example.createCriteria().andUidEqualTo(uid);
		example.setOrderByClause("id desc");
		return rewardMapper.selectByExample(example);
	}

}
