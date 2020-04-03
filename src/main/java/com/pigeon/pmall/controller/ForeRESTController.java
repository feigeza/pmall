package com.pigeon.pmall.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pigeon.pmall.pojo.Order;
import com.pigeon.pmall.pojo.OrderItem;
import com.pigeon.pmall.pojo.Product;
import com.pigeon.pmall.pojo.Review;
import com.pigeon.pmall.pojo.ReviewStatus;
import com.pigeon.pmall.pojo.User;
import com.pigeon.pmall.service.CategoryService;
import com.pigeon.pmall.service.OrderItemService;
import com.pigeon.pmall.service.OrderService;
import com.pigeon.pmall.service.ProductImageService;
import com.pigeon.pmall.service.ProductService;
import com.pigeon.pmall.service.ReviewService;
import com.pigeon.pmall.service.ReviewStatusService;
import com.pigeon.pmall.service.UserService;
import com.pigeon.pmall.util.Result;

/**
 * ForeRESTController,专门用来对应前台页面的路径。实现相应业务。
 * @author wangpan
 *
 */
@RestController
public class ForeRESTController {
	
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@Autowired
	ProductImageService productImageService;
	@Autowired
	UserService userService;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderItemService orderItemService;
	@Autowired
	ReviewService reviewService;
	@Autowired
	ReviewStatusService reviewStatusService;
	
	@GetMapping("/forehome")
	public PageInfo<Product> home(@RequestParam(value="start", defaultValue="1")int start, @RequestParam(value="size", defaultValue="50")int size) {
		PageHelper.startPage(start, size, "id desc");
		List<Product> products= productService.list();
		PageInfo<Product> page = new PageInfo<Product>(products);
		return page;
	}
	
	/**
	 * 注册用户。
	 * @param user
	 * @return
	 */
	@PostMapping("/foreregister")
	public Object register(@RequestBody User user) {
		boolean flag = userService.isExist(user.getName());
		if(flag) {
			String msg = "用户名已经被使用，不能使用。";
			return Result.fail(msg);
		}
		
		userService.add(user);
		
		return Result.success();
	}
	
	/**
	 * 用户登录。将查询到的User放入session中。
	 * @param user
	 * @param session
	 * @return
	 */
	@PostMapping("/forelogin")
	public Object login(@RequestBody User user, HttpSession session) {
		boolean flag = userService.isExist(user.getName());
		if(!flag) {
			String msg = "用户不存在。";
			return Result.fail(msg);
		}
		
		User userFormDB = userService.get(user.getName(), user.getPassword());
		if(null == userFormDB) {
			String msg = "用户名或密码错误。";
			return Result.fail(msg);
		}
		else {
			session.setMaxInactiveInterval(-1); //将session设置为永不过期
			session.setAttribute("user", userFormDB);
			return Result.success();
		}
	}
	
	/**
	 * 在用户管理页面，通过session获取到User，返回给页面。
	 * @param session
	 * @return
	 */
	@GetMapping("/editUser")
	public User editUser(HttpSession session) {
		User user = (User)session.getAttribute("user");
		return user;
	}
	
	/**
	 * 退出登录，移除session中的user参数。
	 * @param session
	 * @return
	 */
	@GetMapping("/forelogout")
	public Object logout(HttpSession session) {
	    session.removeAttribute("user");
	    return Result.success();
	}
	
	/**
	 * 搜索。
	 * @param keyword 搜索内容
	 * @param start 开始页码
	 * @param size 默认50
	 * @return PageInfo<Product>
	 */
	@PostMapping("/foresearch")
	public PageInfo<Product> search(String keyword, @RequestParam(value="start", defaultValue="1")int start, @RequestParam(value="size", defaultValue="50")int size) {
		if(null==keyword)
			keyword = "";
		
		PageHelper.offsetPage(start, size, "id desc");
		List<Product> products = productService.search(keyword);
		PageInfo<Product> page = new PageInfo<Product>(products);
		
		return page;
	}
	
	/**
	 * 根据session来确认是否登录。
	 * @param session
	 * @return
	 */
	@GetMapping("forechecklogin")
	public Object forechecklogin(HttpSession session) {
		User user = (User)session.getAttribute("user");
		if(null != user) {
			return Result.success();
		}
		return Result.fail("未登录");
	}
	
	/**
	 * 点击加入购物车或直接购买后跳转，新增订单项（OrderItem）。
	 * @param pid 买的什么商品
	 * @param number 买了多少
	 * @param session 谁买的
	 * @return 新增的订单项的id
	 */
	@GetMapping("/forebuyone")
	public Object forebuyone(int pid, int number, HttpSession session) {
		int oiid = 0;

		//判断表中是否有存在此商品的订单项（OrderItem），如果有，增加数量；如果没有，新增订单项。
		User user = (User)session.getAttribute("user");
		boolean flag = false;
		List<OrderItem> orderItems = orderItemService.listByUid(user.getId());
		for(OrderItem oi : orderItems) {
			if(oi.getPid() == pid) {
				oi.setNumber(oi.getNumber()+number);
				orderItemService.update(oi);
				flag = true;
				oiid = oi.getId();
				break;
			}
		}
		
		if(!flag) {
			OrderItem oi = new OrderItem();
			oi.setNumber(number);
			oi.setPid(pid);
			oi.setUid(user.getId());
			orderItemService.add(oi);
			oiid = oi.getId();
		}
		
		return oiid;
	}
	
	/**
	 * 这里是确认订单（OrderConfirm）页面，初始化数据访问的。 
	 * 根据从页面传来的oiid的数组，来获取相应的OrderItem订单项。
	 * 返回一个Map，其中有所有的订单项OrderItems，还有总计total。
	 * 
	 * 将查出的OrderItem放入session的"orderItems"参数中，以便后面创建订单Order时使用。
	 * 
	 * @param oiid String[]
	 * @param session
	 * @return
	 */
	@GetMapping("/foreorderconfirm")
	public Object foreorderconfirm(String[] oiid, HttpSession session) {
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		int total = 0;
		int allCount = 0;
		
		for(String orderItemid : oiid) {
			int id = Integer.parseInt(orderItemid);
			OrderItem orderItem = orderItemService.get(id);
			allCount += orderItem.getNumber();
			total += (orderItem.getNumber() * productService.get(orderItem.getPid()).getOriginalPrice());
			orderItems.add(orderItem);
		}
		
		session.setAttribute("orderItems", orderItems);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderItems", orderItems);
		map.put("total", total);
		map.put("allCount", allCount);
		
		return map;
	}
	
	/**
	 * 展示购物车。
	 * 从session中取出User，查出OrderItem中尚未生成订单的数据。
	 * @param session
	 * @return
	 */
	@GetMapping("forecart")
	public Object forecart(HttpSession session) {
		User user = (User)session.getAttribute("user");
		List<OrderItem> orderItems = orderItemService.listByUid(user.getId());
		return orderItems;
	}
	
	/**
	 * 更新订单项中的数量信息。
	 * @param orderItem
	 * @return
	 * @throws Exception
	 */
	@PutMapping("foreChangeOrderItem")
	public Object foreChangeOrderItem(@RequestBody OrderItem orderItem) throws Exception {
		//System.out.println(orderItem);
		orderItemService.update(orderItem);
		return Result.success();
	}
	
	@DeleteMapping("foreDeleteOrderItem/{id}")
	public Object foreDeleteOrderItem(@PathVariable("id")int id) throws Exception {
		orderItemService.delete(id);
		return Result.success();
	}
	
	/**
	 * 创建订单。
	 * @param order
	 * @param session
	 * @return
	 */
	@PostMapping("forecreateorder")
	public Object forecreateorder(@RequestBody Order order, HttpSession session) {
		
		User user = (User)session.getAttribute("user");
		if(null==user) {
			return Result.fail("未登录");
		}
		String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
		order.setOrderCode(orderCode);
		order.setCreateDate(new Date());
		order.setUid(user.getId());
		order.setStatus(OrderService.WAITPAY);
		List<OrderItem> orderItems = (List<OrderItem>)session.getAttribute("orderItems");
		
		float total = orderService.add(order, orderItems);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("oid", order.getId());
		map.put("total", total);
		
		return Result.success(map);
	}
	
	/**
	 * 付款成功，更改状态待发货。
	 * @param oid
	 * @return
	 */
	@GetMapping("forepayed")
	public Object forepayed(int oid) {
		Order order = orderService.get(oid);
		order.setStatus(OrderService.WAITDELIVERY);
		order.setPayDate(new Date());
		orderService.update(order);
		return order;
	}
	
	/**
	 * 我的订单页面。
	 * 根据session中的user和页面传来的status，查询出对应的订单。
	 * @param status
	 * @param session
	 * @return
	 */
	@GetMapping("forebought")
	public Object forebought(String status, HttpSession session) {
		
		User user = (User)session.getAttribute("user");
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<Order> orders = orderService.list(user.getId(), status);
		map.put("orders", orders);
		
		getOrderItemsInfoByOrders(map, orders);
		
		return map;
	}
	
	private void getOrderItemsInfoByOrders(Map<String,Object> map, List<Order> orders) {
		List<List<OrderItem>> orderItems = new ArrayList<List<OrderItem>>();
		List<List<Product>> products = new ArrayList<List<Product>>();
		List<Float> totals = new ArrayList<Float>();
		List<Integer> allCounts = new ArrayList<Integer>();
		for(Order order : orders) {
			float total = 0;
			int allCount = 0;
			List<OrderItem> orderItem = orderItemService.listByOid(order.getId());
			orderItems.add(orderItem);
			List<Product> product = new ArrayList<Product>();
			for(OrderItem o : orderItem) {
				Product p = productService.get(o.getPid());
				product.add(p);
				total += o.getNumber() * p.getOriginalPrice();
				allCount += o.getNumber();
			}
			products.add(product);
			totals.add(total);
			allCounts.add(allCount);
		}
		map.put("orderItems", orderItems);
		map.put("products", products);
		map.put("totals", totals);
		map.put("allCounts", allCounts);
	}
	
	/**
	 * 将订单删除。（把status改成delete）
	 * @param oid
	 * @return
	 */
	@DeleteMapping("foredeleteorder")
	public Object foredeleteorder(int oid) {
		Order order = orderService.get(oid);
		order.setStatus(OrderService.DELETE);
		orderService.update(order);
		return Result.success();
	}
	
	/**
	 * 订单确认收货
	 * @param oid
	 * @return
	 */
	@GetMapping("forereceived")
	public Object forereceived(int oid) {
		Order order = orderService.get(oid);
		order.setStatus(OrderService.WAITREVIEW);
		order.setConfirmDate(new Date());
		orderService.update(order);
		return Result.success();
	}
	
	/**
	 * 点击去评价后，跳转的评价编辑页面。
	 * 取回数据。
	 * @param oid
	 * @return
	 */
	@GetMapping("foreeditreview")
	public Object foreeditreview(int oid) {
		Map<String,Object> map = new HashMap<String,Object>();
		Order order = orderService.get(oid);
		List<OrderItem> orderItems = orderItemService.listByOid(order.getId());
		List<Product> products = new ArrayList<Product>();
		for(OrderItem orderItem : orderItems) {
			Product product = productService.get(orderItem.getPid());
			products.add(product);
		}
		map.put("order", order);
		map.put("orderItems", orderItems);
		map.put("products", products);
		
		return map;
	}
	
	/**
	 * 添加评价
	 * 修改订单Order状态成finish
	 * @param oid
	 * @param pid
	 * @param content
	 * @param session
	 * @return
	 */
	@PostMapping("foredoreview")
	public Object foredoreview(int oid, int pid, String content, HttpSession session) {
		Order o = orderService.get(oid);
	    o.setStatus(OrderService.FINISH);
	    orderService.update(o);
	 
	    content = HtmlUtils.htmlEscape(content);
	    User user =(User)session.getAttribute("user");
	    Review review = new Review();
	    review.setContent(content);
	    review.setPid(pid);
	    review.setCreateDate(new Date());
	    review.setUid(user.getId());
	    reviewService.add(review);
	    return Result.success();
	}
	
	/**
	 * 查询评价Review
	 * @param pid
	 * @return
	 */
	@GetMapping("forereview")
	public Object forereview(int pid) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		int count = 0;
		List<Review> reviews = reviewService.listByPid(pid);
		if(reviews.isEmpty()) {
			return Result.fail("此产品暂无评价");
		}
		List<User> users = new ArrayList<User>();
		List<ReviewStatus> reviewStatus = new ArrayList<ReviewStatus>();
		for(Review review: reviews) {
			User user = userService.get(review.getUid());
			users.add(user);
			ReviewStatus rs = reviewStatusService.get(review.getId());
			reviewStatus.add(rs);
			count++;
		}
		
		map.put("firstReview", reviews.get(0));
		map.put("user", users.get(0));
		map.put("count", count);
		
		//排序，将置顶的评论放前面来。
		if(null!=reviewStatus) {
			int index = 0;
			for(int i=0; i<reviewStatus.size(); i++) {
				if(1==reviewStatus.get(i).getSticky()) {
					swap(reviews,i,index);
					swap(users,i,index);
					swap(reviewStatus,i,index);
					index++;
				}
			}
		}
		
		map.put("reviews", reviews);
		map.put("users", users);
		map.put("reviewStatus", reviewStatus);
		
		return map;
	}
	
	public static <T> void swap(List<T> list, int oldIdx, int newIdx) {
		T temp = list.get(newIdx);
		
		list.set(newIdx, list.get(oldIdx));
		list.set(oldIdx, temp);
	}
	
}
