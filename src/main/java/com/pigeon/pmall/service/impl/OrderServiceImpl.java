package com.pigeon.pmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pigeon.pmall.mapper.OrderMapper;
import com.pigeon.pmall.pojo.Order;
import com.pigeon.pmall.pojo.OrderExample;
import com.pigeon.pmall.pojo.OrderItem;
import com.pigeon.pmall.service.OrderItemService;
import com.pigeon.pmall.service.OrderService;
import com.pigeon.pmall.service.ProductService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderMapper orderMapper;
	@Autowired
	OrderItemService orderItemService;
	@Autowired
	ProductService productService;
	
	@Override
	public void add(Order order) {
		orderMapper.insert(order);
	}
	
	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
	public float add(Order order, List<OrderItem> orderItems) {
		float total = 0;
		add(order);
		
		for(OrderItem orderItem : orderItems) {
			orderItem.setOid(order.getId());
			orderItemService.update(orderItem);
			total += productService.get(orderItem.getPid()).getOriginalPrice() * orderItem.getNumber();
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
