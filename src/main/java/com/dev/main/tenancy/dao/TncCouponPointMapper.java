package com.dev.main.tenancy.dao;

import com.dev.main.tenancy.domain.TncCouponPoint;

public interface TncCouponPointMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TncCouponPoint record);

    int insertSelective(TncCouponPoint record);

    TncCouponPoint selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TncCouponPoint record);

    int updateByPrimaryKey(TncCouponPoint record);
}