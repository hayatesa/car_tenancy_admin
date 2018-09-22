package com.dev.main.tenancy.dao;

import com.dev.main.tenancy.domain.TncCoupon;

import java.util.List;

public interface TncCouponMapper extends BaseMapper<TncCoupon> {
    int deleteByPrimaryKey(Long id);

    int insert(TncCoupon record);

    int insertSelective(TncCoupon record);

    TncCoupon selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TncCoupon record);

    int updateByPrimaryKey(TncCoupon record);

    /**
     * 查找使用期限已过的优惠券，status = 0（未使用）且到期时间 < 当前时间,每次更新记录数上限1000条
     * @return 受影响记录数
     */
    int setExpired();
}
