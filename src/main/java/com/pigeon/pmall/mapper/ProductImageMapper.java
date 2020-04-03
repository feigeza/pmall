package com.pigeon.pmall.mapper;

import com.pigeon.pmall.pojo.ProductImage;
import com.pigeon.pmall.pojo.ProductImageExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductImage record);

    int insertSelective(ProductImage record);

    List<ProductImage> selectByExample(ProductImageExample example);

    ProductImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductImage record);

    int updateByPrimaryKey(ProductImage record);
}