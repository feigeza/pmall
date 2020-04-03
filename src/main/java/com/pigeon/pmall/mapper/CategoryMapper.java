package com.pigeon.pmall.mapper;

import com.pigeon.pmall.pojo.Category;
import com.pigeon.pmall.pojo.CategoryExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}