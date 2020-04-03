package com.pigeon.pmall.mapper;

import com.pigeon.pmall.pojo.Review;
import com.pigeon.pmall.pojo.ReviewExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Review record);

    int insertSelective(Review record);

    List<Review> selectByExample(ReviewExample example);

    Review selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Review record);

    int updateByPrimaryKey(Review record);
}