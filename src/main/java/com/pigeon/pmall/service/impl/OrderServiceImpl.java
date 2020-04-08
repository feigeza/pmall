package com.pigeon.pmall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pigeon.pmall.mapper.OrderMapper;
import com.pigeon.pmall.pojo.Order;
import com.pigeon.pmall.pojo.OrderExample;
import com.pigeon.pmall.pojo.OrderItem;
import com.pigeon.pmall.pojo.Reward;
import com.pigeon.pmall.service.OrderItemService;
import com.pigeon.pmall.service.OrderService;
import com.pigeon.pmall.service.ProductService;
import com.pigeon.pmall.service.RewardService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderMapper orderMapper;
	@Autowired
	OrderItemService orderItemService;
	@Autowired
	ProductService productService;
	@Autowired
	RewardService rewardService;
	
	@Override
	public void add(Order order) {
		orderMapper.insert(order);
	}
	
	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
	public float add(Order order, List<OrderItem> orderItems) {
		add(order); //先添加，获得order的id，并给到orderItem
		float total = 0;
		
		for(OrderItem orderItem : orderItems) {
			float subTotal = productService.get(orderItem.getPid()).getOriginalPrice() * orderItem.getNumber();
			orderItem.setOid(order.getId());
			orderItem.setSubTotal(subTotal);
			orderItemService.update(orderItem);
			total += subTotal;
		}
		
		total = total + order.getPostage() - order.getReduce();
		order.setTotal(total);
		update(order);
		
		//减少积分
		if(0!=order.getReduce()) {
			Reward reward = new Reward();
			reward.setUid(order.getUid());
			reward.setOperation(RewardService.USE);
			List<Reward> rewards = rewardService.list(order.getUid());
			Float totalReward = rewards.get(0).getTotal();
			totalReward -= order.getReduce()*100;
			reward.setTotal(totalReward);
			reward.setCreateDate(new Date());
			rewardService.add(reward);
		}
		
		return total;
	}
	
	@Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }
	
	@Override
	public List<Order> list() {
		OrderExample example = new OrderExample();
		example.setOrderByClause("id desc");
		return orderMapper.selectByExample(example);
	}

	@Override
	public List<Order> list(int uid, String status) {
		OrderExample example = new OrderExample();
		//如果我的订单页面，查询所有非删除的订单
		if("all".equals(status)) {
			example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(OrderService.DELETE);
		}
		else {
			example.createCriteria().andUidEqualTo(uid).andStatusEqualTo(status);
		}
		example.setOrderByClause("id desc");
		return orderMapper.selectByExample(example);
	}

}
