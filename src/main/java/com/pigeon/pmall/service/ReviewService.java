package com.pigeon.pmall.service;

import java.util.List;

import com.pigeon.pmall.pojo.Review;


public interface ReviewService{
    void add(Review review);

    void delete(int id);

    void update(Review review);
    
    Review get(int id);
    
    List<Review> list();
    
    List<Review> listByPid(int pid);
}