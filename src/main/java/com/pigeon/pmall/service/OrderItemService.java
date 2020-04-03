package com.pigeon.pmall.service;

import java.util.List;

import com.pigeon.pmall.pojo.Order;
import com.pigeon.pmall.pojo.OrderItem;

public interface OrderItemService {

	void add(OrderItem orderItem);

	void delete(int id);

	void update(OrderItem orderItem);

	OrderItem get(int id);
	
	/**
	 * 根据oid（Order表）来获取其相应的OrderItem
	 * 
	 * @param oid
	 * @return 一个OrderItem的List
	 */
	List<OrderItem> listByOid(int oid);

	/**
	 * 根据uid（User表）来获取相应的OrderItem
	 * 
	 * @param uid
	 * @return
	 */
	List<OrderItem> listByUid(int uid);

	/**
	 * 通过传来的Order，查询出订单对应的订单项，然后计算出总金额和总数量 赋值给Order。
	 * 
	 * @param os 一个Order的List
	 */
	void fill(List<Order> orders);

	void fill(Order o);

}
