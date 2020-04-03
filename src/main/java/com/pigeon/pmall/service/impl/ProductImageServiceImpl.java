package com.pigeon.pmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pigeon.pmall.mapper.ProductImageMapper;
import com.pigeon.pmall.pojo.OrderItem;
import com.pigeon.pmall.pojo.Product;
import com.pigeon.pmall.pojo.ProductImage;
import com.pigeon.pmall.pojo.ProductImageExample;
import com.pigeon.pmall.service.ProductImageService;

@Service
public class ProductImageServiceImpl implements ProductImageService {

	@Autowired
	ProductImageMapper productImageMapper;

	@Override
	public List<ProductImage> listSingleProductImages(Product product) {
		return list(product.getId(), TYPE_SINGLE);
	}

	@Override
	public List<ProductImage> listDetailProductImages(Product product) {
		return list(product.getId(), TYPE_DETAIL);
	}
	
	@Override
	public List<ProductImage> list(int pid, String type) {
		ProductImageExample example = new ProductImageExample();
		example.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);
		example.setOrderByClause("id desc");
		return productImageMapper.selectByExample(example);
	}

	@Override
	public void add(ProductImage pi) {
		productImageMapper.insert(pi);
	}

	@Override
	public void delete(int id) {
		productImageMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(ProductImage pi) {
		productImageMapper.updateByPrimaryKeySelective(pi);
	}

	@Override
	public ProductImage get(int id) {
		return productImageMapper.selectByPrimaryKey(id);
	}

	@Override
	public void setFirstProductImage(Product product) {
		List<ProductImage> singleImages = listSingleProductImages(product);
		if(!singleImages.isEmpty())
			product.setFirstProductImage(singleImages.get(0));
		else
			product.setFirstProductImage(new ProductImage()); //这样做是考虑到产品还没有来得及设置图片，但是在订单后台管理里查看订单项的对应产品图片。
	}
	
	@Override
	public void setFirstProductImages(List<Product> products) {
		for(Product p : products) {
			setFirstProductImage(p);
		}
	}

}
