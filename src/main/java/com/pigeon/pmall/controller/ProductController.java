package com.pigeon.pmall.controller;

import java.util.Date;
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
import com.pigeon.pmall.pojo.Product;
import com.pigeon.pmall.service.ProductImageService;
import com.pigeon.pmall.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
	ProductImageService productImageService;
	
	@GetMapping("/categories/{cid}/products")
	public PageInfo<Product> list(@PathVariable int cid, @RequestParam(value="start", defaultValue="1")int start, @RequestParam(value="size", defaultValue="5")int size) throws Exception {
		
		PageHelper.startPage(start, size, "id desc");
		List<Product> products = productService.list(cid);
		PageInfo<Product> page = new PageInfo<>(products, 5);

		return page;
	}
	
	@GetMapping("/products/{id}")
    public Product get(@PathVariable("id") int id) throws Exception {
        Product bean=productService.get(id);
        return bean;
    }
     
    @PostMapping("/products")
    public Object add(@RequestBody Product bean) throws Exception {
        bean.setCreateDate(new Date());
        productService.add(bean);
        return bean;
    }
     
    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable("id") int id)  throws Exception {
        productService.delete(id);
        return null;
    }
     
    @PutMapping("/products")
    public Object update(@RequestBody Product bean) throws Exception {
        productService.update(bean);
        return bean;
    }
	
}
