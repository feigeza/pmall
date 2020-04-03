package com.pigeon.pmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pigeon.pmall.mapper.PropertyMapper;
import com.pigeon.pmall.pojo.Product;
import com.pigeon.pmall.pojo.Property;
import com.pigeon.pmall.pojo.PropertyExample;
import com.pigeon.pmall.service.ProductService;
import com.pigeon.pmall.service.PropertyService;

@Service
public class PropertyServiceImpl implements PropertyService {
	@Autowired
	PropertyMapper propertyMapper;
	@Autowired
	ProductService productService;
	
	@Override
	public void add(Property p) {
		propertyMapper.insert(p);
	}

	@Override
	public void delete(int id) {
		propertyMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Property p) {
		propertyMapper.updateByPrimaryKeySelective(p);
	}

	@Override
	public Property get(int id) {
		return propertyMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Property> list(int cid) {
		PropertyExample example = new PropertyExample();
		example.createCriteria().andCidEqualTo(cid);
		example.setOrderByClause("id desc");
		return propertyMapper.selectByExample(example);
	}

	@Override
	public List<Property> listByPid(int pid) {
		Product product = productService.get(pid);
		return list(product.getCid());
	}

}
