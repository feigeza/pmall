package com.pigeon.pmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pigeon.pmall.pojo.PropertyValue;
import com.pigeon.pmall.service.ProductService;
import com.pigeon.pmall.service.PropertyValueService;

@RestController
public class PropertyValueController {
	@Autowired
	PropertyValueService propertyValueService;
	@Autowired
	ProductService productService;
	
	@GetMapping("/products/{pid}/propertyValues")
	public List<PropertyValue> list(@PathVariable("pid") int pid) throws Exception {
		propertyValueService.init(productService.get(pid));
		
		return propertyValueService.list(pid);
	}

	@PutMapping("/propertyValues")
    public Object update(@RequestBody PropertyValue bean) throws Exception {
        propertyValueService.update(bean);
        return bean;
    }
	
}
