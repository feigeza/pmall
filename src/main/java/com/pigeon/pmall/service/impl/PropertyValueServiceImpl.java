package com.pigeon.pmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pigeon.pmall.mapper.PropertyValueMapper;
import com.pigeon.pmall.pojo.Product;
import com.pigeon.pmall.pojo.Property;
import com.pigeon.pmall.pojo.PropertyValue;
import com.pigeon.pmall.pojo.PropertyValueExample;
import com.pigeon.pmall.service.PropertyService;
import com.pigeon.pmall.service.PropertyValueService;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {

	@Autowired
	PropertyValueMapper propertyValueMapper;
	@Autowired
	PropertyService propertyService;

	@Override
	public void init(Product product) {
		//通过cid获取相应分类下的所有属性
		List<Property> properties = propertyService.list(product.getCid());
		//遍历属性，如果在属性值表(propertyValue)中没有，则初始化一个属性值(propertyValue)
		for(Property property : properties) {
			PropertyValue pv = get(product.getId(), property.getId());
			if(null==pv) {
				pv = new PropertyValue();
				pv.setPid(product.getId());
				pv.setPtid(property.getId());
				propertyValueMapper.insert(pv);
			}
		}
	}

	@Override
	public void update(PropertyValue pv) {
		propertyValueMapper.updateByPrimaryKeySelective(pv);
	}

	@Override
	public PropertyValue get(int pid, int ptid) {
		PropertyValueExample example = new PropertyValueExample();
		example.createCriteria().andPidEqualTo(pid).andPtidEqualTo(ptid);
		example.setOrderByClause("id desc");
		List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(example);
		if(propertyValues.isEmpty())
			return null;
		return propertyValues.get(0);
	}
	
	@Override
	public List<PropertyValue> list(int pid) {
		PropertyValueExample example = new PropertyValueExample();
		example.createCriteria().andPidEqualTo(pid);
		example.setOrderByClause("ptid desc");
		return propertyValueMapper.selectByExample(example);
	}

}
