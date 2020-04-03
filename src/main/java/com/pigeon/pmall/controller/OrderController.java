package com.pigeon.pmall.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pigeon.pmall.pojo.Order;
import com.pigeon.pmall.pojo.OrderItem;
import com.pigeon.pmall.pojo.Product;
import com.pigeon.pmall.service.OrderItemService;
import com.pigeon.pmall.service.OrderService;
import com.pigeon.pmall.service.ProductService;
import com.pigeon.pmall.util.Result;

@RestController
public class OrderController {
	
	@Autowired
	OrderService orderService;
	@Autowired
	OrderItemService orderItemService;
	@Autowired
	ProductService productService;
	
	@GetMapping("/orders")
	public PageInfo<Order> list(@RequestParam(value="start", defaultValue="1")int start, @RequestParam(value="size", defaultValue="5")int size) throws Exception {
		
		PageHelper.startPage(start, size, "id desc");
		List<Order> orders = orderService.list();
		orderItemService.fill(orders);
		PageInfo<Order> page = new PageInfo<>(orders, 5);
		
		return page;
	}

	@GetMapping("/orderItems/{id}")
	public List<OrderItem> get(@PathVariable("id")int id) throws Exception {
		return orderItemService.listByOid(id);
	}
	
	@GetMapping("/orderItems/{id}/products")
	public List<Product> getProducts(@PathVariable("id")int id) throws Exception {
		List<OrderItem> orderItems = orderItemService.listByOid(id);
		List<Product> products = new ArrayList<Product>();
		for(OrderItem oi : orderItems) {
			Product product = productService.get(oi.getPid());
			products.add(product);
		}
		return products;
	}
	
	@PutMapping("deliveryOrder/{oid}")
    public Object deliveryOrder(@PathVariable int oid) throws IOException {
        Order o = orderService.get(oid);
        o.setDeliveryDate(new Date());
        o.setStatus(OrderService.WAITCONFIRM);
        orderService.update(o);
        return Result.success();
    }
	
}
