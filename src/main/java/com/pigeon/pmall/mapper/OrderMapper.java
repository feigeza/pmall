package com.pigeon.pmall.mapper;

import com.pigeon.pmall.pojo.Order;
import com.pigeon.pmall.pojo.OrderExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}