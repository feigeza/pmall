package com.pigeon.pmall.service;

import java.util.List;

import com.pigeon.pmall.pojo.Product;
import com.pigeon.pmall.pojo.PropertyValue;

public interface PropertyValueService {
    void init(Product product);

    void update(PropertyValue pv);

    PropertyValue get(int pid, int ptid);
    
	List<PropertyValue> list(int pid);
}
