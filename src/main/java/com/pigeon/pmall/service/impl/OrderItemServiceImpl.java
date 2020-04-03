package com.pigeon.pmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pigeon.pmall.mapper.OrderItemMapper;
import com.pigeon.pmall.pojo.Order;
import com.pigeon.pmall.pojo.OrderItem;
import com.pigeon.pmall.pojo.OrderItemExample;
import com.pigeon.pmall.service.OrderItemService;
import com.pigeon.pmall.service.ProductService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	OrderItemMapper orderItemMapper;
	@Autowired
	ProductService productService;


	@Override
	public void add(OrderItem orderItem) {
		orderItemMapper.insert(orderItem);
	}

	@Override
	public void delete(int id) {
		orderItemMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(OrderItem orderItem) {
		orderItemMapper.updateByPrimaryKeySelective(orderItem);
	}
	

	@Override
	public OrderItem get(int id) {
		return orderItemMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<OrderItem> listByOid(int oid) {
		OrderItemExample example = new OrderItemExample();
		example.createCriteria().andOidEqualTo(oid);
		example.setOrderByClause("id desc");
		return orderItemMapper.selectByExample(example);
	}
	
	@Override
	public List<OrderItem> listByUid(int uid) {
		OrderItemExample example = new OrderItemExample();
		example.createCriteria().andUidEqualTo(uid).andOidIsNull();
		example.setOrderByClause("id desc");
		return orderItemMapper.selectByExample(example);
	}

	@Override
	public void fill(List<Order> orders) {
		for(Order o : orders)
			fill(o);
	}

	@Override
	public void fill(Order order) {
		List<OrderItem> orderItems = listByOid(order.getId());
		float total = 0;
		int totalNumber = 0;
		for(OrderItem oi : orderItems) {
			total += oi.getNumber() * (productService.get(oi.getPid()).getPromotePrice());
			totalNumber += oi.getNumber();
		}
		order.setTotal(total);
		order.setTotalNumber(totalNumber);
	}

}
