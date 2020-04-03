package com.pigeon.pmall.mapper;

import com.pigeon.pmall.pojo.OrderItem;
import com.pigeon.pmall.pojo.OrderItemExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    List<OrderItem> selectByExample(OrderItemExample example);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
}