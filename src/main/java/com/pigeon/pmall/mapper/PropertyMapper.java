package com.pigeon.pmall.mapper;

import com.pigeon.pmall.pojo.Property;
import com.pigeon.pmall.pojo.PropertyExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PropertyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Property record);

    int insertSelective(Property record);

    List<Property> selectByExample(PropertyExample example);

    Property selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Property record);

    int updateByPrimaryKey(Property record);
}