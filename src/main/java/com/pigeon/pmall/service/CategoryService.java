package com.pigeon.pmall.service;

import java.util.List;

import com.pigeon.pmall.pojo.Category;

public interface CategoryService{
    void add(Category category);

    void delete(int id);

    void update(Category category);
    
    Category get(int id);
    
    List<Category> list();
}