package com.pigeon.pmall.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *  ForePageController 来专门做前台页面的跳转。
 * @author wangpan
 *
 */
@Controller
public class ForePageController {
	
	@GetMapping(value="/")
	public String index(){
		return "redirect:home";
	}
	@GetMapping(value="/home")
	public String home(){
		return "fore/home";
	}
	@GetMapping(value="/cart")
	public String cart(){
		return "fore/cart";
	}
	@GetMapping(value="/product")
	public String product(){
		return "fore/product";
	}
	@GetMapping(value="/category")
	public String category(){
		return "fore/category";
	}
	@GetMapping(value="/user")
	public String user(){
		return "fore/user";
	}
	@GetMapping(value="/register")
	public String register(){
		return "fore/register";
	}
	@GetMapping(value="/login")
	public String login(){
		return "fore/login";
	}
	@GetMapping(value="/user_edit")
	public String edituser(){
		return "fore/editUser";
	}
	@GetMapping(value="/review_edit")
	public String editReview(){
		return "fore/editReview";
	}
	@GetMapping(value="/search")
	public String searchResult(){
		return "fore/search";
	}
	@GetMapping(value="/orderConfirm")
	public String buy(){
		return "fore/orderConfirm";
	}
	@GetMapping(value="/pay")
	public String alipay(){
		return "fore/pay";
	}
	@GetMapping(value="/myorder")
	public String myorder(){
		return "fore/myorder";
	}
	@GetMapping(value="/reward")
	public String reward(){
		return "fore/reward";
	}
	@GetMapping(value="/vip")
	public String vip(){
		return "fore/vip";
	}
	@GetMapping(value="/chat")
	public String chat(){
		return "fore/chat";
	}
	
	/*
	 * @GetMapping(value="/bought") public String bought(){ return "fore/bought"; }
	 * 
	 * @GetMapping(value="/confirmPay") public String confirmPay(){ return
	 * "fore/confirmPay"; }
	 * 
	 * @GetMapping(value="/orderConfirmed") public String orderConfirmed(){ return
	 * "fore/orderConfirmed"; }
	 * 
	 * @GetMapping(value="/payed") public String payed(){ return "fore/payed"; }
	 */
	
	/*
	 * @GetMapping("/forelogout") public String logout( ) { Subject subject =
	 * SecurityUtils.getSubject(); if(subject.isAuthenticated()) subject.logout();
	 * return "redirect:home"; }
	 */
}
