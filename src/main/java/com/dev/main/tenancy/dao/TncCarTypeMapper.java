package com.dev.main.tenancy.dao;

import com.dev.main.tenancy.domain.TncCarType;

import java.util.List;

public interface TncCarTypeMapper extends BaseMapper<TncCarType> {
    int deleteByPrimaryKey(Long id);

    int insert(TncCarType record);

    int insertSelective(TncCarType record);

    TncCarType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TncCarType record);

    int updateByPrimaryKey(TncCarType record);

    List<TncCarType> selectAllCarType();
}
