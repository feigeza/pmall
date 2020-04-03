package com.pigeon.pmall.mapper;

import com.pigeon.pmall.pojo.Reward;
import com.pigeon.pmall.pojo.RewardExample;
import java.util.List;

public interface RewardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Reward record);

    int insertSelective(Reward record);

    List<Reward> selectByExample(RewardExample example);

    Reward selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Reward record);

    int updateByPrimaryKey(Reward record);
}