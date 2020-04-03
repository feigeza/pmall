package com.pigeon.pmall.service;

import java.util.List;

import com.pigeon.pmall.pojo.User;

public interface UserService {
	void add(User user);
    void delete(int id);
    void update(User user);
    User get(int id);
	List<User> list();
	
	/**
	 * 在注册时，判断此用户名是否使用过。存在则返回真。
	 * @param name 用户名
	 * @return boolean
	 */
	boolean isExist(String name);
	
	/**
	 * 登录方法。根据用户名和密码得到User。
	 * @param name 用户名
	 * @param password 密码
	 * @return User
	 */
	User get(String name, String password);
	
}
