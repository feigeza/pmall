package com.pigeon.pmall.service;

import java.util.List;

import com.pigeon.pmall.pojo.OrderItem;
import com.pigeon.pmall.pojo.Product;
import com.pigeon.pmall.pojo.ProductImage;

public interface ProductImageService {
	
	String TYPE_SINGLE = "single";
    String TYPE_DETAIL = "detail";
	
    List<ProductImage> listSingleProductImages(Product product);
    List<ProductImage> listDetailProductImages(Product product);
      
	List<ProductImage> list(int pid, String type);
	
	void add(ProductImage pi);
	
    void delete(int id);
    
    void update(ProductImage pi);
    
    ProductImage get(int id);
    
    void setFirstProductImage(Product product);
    
    void setFirstProductImages(List<Product> products);
    
}
