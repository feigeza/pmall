package com.pigeon.pmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pigeon.pmall.mapper.ProductMapper;
import com.pigeon.pmall.pojo.Product;
import com.pigeon.pmall.pojo.ProductExample;
import com.pigeon.pmall.service.ProductImageService;
import com.pigeon.pmall.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductMapper productMapper;
	@Autowired
	ProductImageService productImageService;

	@Override
	public void add(Product product) {
		productMapper.insert(product);
	}

	@Override
	public void delete(int id) {
		productMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Product product) {
		productMapper.updateByPrimaryKeySelective(product);
	}

	@Override
	public Product get(int id) {
		Product product = productMapper.selectByPrimaryKey(id);
		productImageService.setFirstProductImage(product);
		return product;
	}
	
	@Override
	public List<Product> list() {
		ProductExample example = new ProductExample();
		example.setOrderByClause("id desc");
		List<Product> products = productMapper.selectByExample(example);
		productImageService.setFirstProductImages(products);
		return products;
	}
	
	@Override
	public List<Product> list(int cid) {
		ProductExample example = new ProductExample();
		example.createCriteria().andCidEqualTo(cid);
		example.setOrderByClause("id desc");
		List<Product> products = productMapper.selectByExample(example);
		productImageService.setFirstProductImages(products);
		return products;
	}

	@Override
	public List<Product> search(String keyword) {
		ProductExample example = new ProductExample();
		example.createCriteria().andNameLike("%" + keyword + "%");
		example.setOrderByClause("id desc");
		List<Product> products = productMapper.selectByExample(example);
		productImageService.setFirstProductImages(products);
		return products;
	}

}
