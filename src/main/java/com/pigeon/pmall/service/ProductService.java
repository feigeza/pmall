package com.pigeon.pmall.service;

import java.util.List;

import com.pigeon.pmall.pojo.Product;

public interface ProductService {
    void add(Product product);

    void delete(int id);

    void update(Product product);
    
    Product get(int id);
    
    /**
     * 获取所有的商品
     * @return
     */
    List<Product> list();
    
    /**
     * 根据分类Category的cid来获取相应的商品Product
     * @param cid 
     * @return
     */
    List<Product> list(int cid);
    
    /**
     * 搜索产品。
     * @param keyword 输入的关键字
     * @return List<Product>
     */
    List<Product> search(String keyword);
    
}
