package com.pigeon.pmall.mapper;

import com.pigeon.pmall.pojo.ReviewStatus;
import com.pigeon.pmall.pojo.ReviewStatusExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewStatusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReviewStatus record);

    int insertSelective(ReviewStatus record);

    List<ReviewStatus> selectByExample(ReviewStatusExample example);

    ReviewStatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReviewStatus record);

    int updateByPrimaryKey(ReviewStatus record);
}