package com.dev.main.tenancy.dao;

import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.domain.TncCar;
import com.dev.main.tenancy.vo.TncCarVo;

import java.util.List;

public interface TncCarMapper extends BaseMapper<TncCar> {
    int deleteByPrimaryKey(Long id);

    int insert(TncCar record);

    int insertSelective(TncCar record);

    TncCar selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TncCar record);

    int updateByPrimaryKey(TncCar record);

    List<TncCarVo> getCarList(QueryObject queryObject);

}
