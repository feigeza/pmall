package com.pigeon.pmall.service;

import java.util.List;

import com.pigeon.pmall.pojo.Order;
import com.pigeon.pmall.pojo.OrderItem;

public interface OrderService {
	String WAITPAY = "waitPay";
    String WAITDELIVERY = "waitDelivery";
    String WAITCONFIRM = "waitConfirm";
    String WAITREVIEW = "waitReview";
    String FINISH = "finish";
    String DELETE = "delete";
    
    void add(Order order);
    
    float add(Order order, List<OrderItem> orderItems);
    
    void update(Order order);
    
    Order get(int id);
    
    List<Order> list();
    
    /**
     * 根据订单状态查询个人订单。
     * @param uid
     * @param status
     * @return
     */
    List<Order> list(int uid, String status);
    
}
