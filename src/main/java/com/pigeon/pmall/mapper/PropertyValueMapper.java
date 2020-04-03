package com.pigeon.pmall.mapper;

import com.pigeon.pmall.pojo.PropertyValue;
import com.pigeon.pmall.pojo.PropertyValueExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PropertyValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyValue record);

    int insertSelective(PropertyValue record);

    List<PropertyValue> selectByExample(PropertyValueExample example);

    PropertyValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyValue record);

    int updateByPrimaryKey(PropertyValue record);
}