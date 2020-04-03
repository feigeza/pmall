package com.pigeon.pmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pigeon.pmall.mapper.UserMapper;
import com.pigeon.pmall.pojo.User;
import com.pigeon.pmall.pojo.UserExample;
import com.pigeon.pmall.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	
	@Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delete(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }
	
	@Override
	public List<User> list() {
		UserExample example = new UserExample();
		example.setOrderByClause("id desc");
		return userMapper.selectByExample(example);
	}

	@Override
	public boolean isExist(String name) {
		UserExample example = new UserExample();
		example.createCriteria().andNameEqualTo(name);
		List<User> users = userMapper.selectByExample(example);
		
		return users.isEmpty() ? false : true;
	}

	@Override
	public User get(String name, String password) {
		UserExample example = new UserExample();
		example.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
		List<User> users = userMapper.selectByExample(example);
		return users.isEmpty() ? null : users.get(0); //但其实在controller就会判断是否存在，这里是一定能取得到数据的。
	}

}
