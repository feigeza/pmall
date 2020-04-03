package com.pigeon.pmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pigeon.pmall.pojo.Property;
import com.pigeon.pmall.service.PropertyService;

@RestController
public class PropertyController {
	@Autowired
	PropertyService propertyService;
	
	@GetMapping("/categories/{cid}/properties")
	public PageInfo<Property> list(@PathVariable int cid, @RequestParam(value="start", defaultValue="1")int start, @RequestParam(value="size", defaultValue="5")int size) throws Exception {
		
		PageHelper.startPage(start, size, "id desc");
		List<Property> properties = propertyService.list(cid);
		PageInfo<Property> page = new PageInfo<>(properties, 5);
		
		return page;
	}
	
	/**
	 * 根据商品Product来查询对应的属性Property
	 * @param pid  
	 * @param start
	 * @param size
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/products/{pid}/properties")
	public List<Property> listByPid(@PathVariable int pid) throws Exception {
		
		List<Property> properties = propertyService.listByPid(pid);
		
		return properties;
	}
	
	@GetMapping("/properties/{id}")
	public Property get(@PathVariable int id) throws Exception {
		Property bean = propertyService.get(id);
		return bean;
	}
	
	@PostMapping("/properties")
	public Property add(@RequestBody Property bean) throws Exception {
		//System.out.println("--------:"+bean);
		propertyService.add(bean);
		return bean;
	}
	
	@DeleteMapping("/properties/{id}")
	public String delete(@PathVariable int id) throws Exception {
		propertyService.delete(id);
		return null;
	}
	
	@PutMapping("/properties")
	public Object update(@RequestBody Property bean) throws Exception {
		propertyService.update(bean);
		return bean;
	}
	
}
