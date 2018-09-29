package com.dev.main.tenancy.dao;

import com.dev.main.tenancy.domain.TncCarPic;

import java.util.List;

public interface TncCarPicMapper extends BaseMapper<TncCarPic> {
    int deleteByPrimaryKey(Long id);

    int insert(TncCarPic record);

    int insertSelective(TncCarPic record);

    TncCarPic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TncCarPic record);

    int updateByPrimaryKey(TncCarPic record);

    List<TncCarPic> selectByCarId(byte carId);
}
