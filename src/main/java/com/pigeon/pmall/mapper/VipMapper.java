package com.pigeon.pmall.mapper;

import com.pigeon.pmall.pojo.Vip;
import com.pigeon.pmall.pojo.VipExample;
import java.util.List;

public interface VipMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Vip record);

    int insertSelective(Vip record);

    List<Vip> selectByExample(VipExample example);

    Vip selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Vip record);

    int updateByPrimaryKey(Vip record);
}