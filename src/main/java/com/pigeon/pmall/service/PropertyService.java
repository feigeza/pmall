package com.pigeon.pmall.service;

import java.util.List;

import com.pigeon.pmall.pojo.Property;

public interface PropertyService {
	void add(Property p);
	
    void delete(int id);
    
    void update(Property p);
    
    Property get(int id);
    
    List<Property> list(int cid);
    
    List<Property> listByPid(int pid);
}
