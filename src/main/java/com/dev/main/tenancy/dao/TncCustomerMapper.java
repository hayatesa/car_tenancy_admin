package com.dev.main.tenancy.dao;

import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.domain.TncCustomer;
import com.dev.main.tenancy.vo.TncCustomerVo;

import java.util.List;

public interface TncCustomerMapper extends BaseMapper<TncCustomer> {
    int deleteByPrimaryKey(Long id);

    int insert(TncCustomer record);

    int insertSelective(TncCustomer record);

    TncCustomer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TncCustomer record);

    int updateByPrimaryKey(TncCustomer record);

    List<TncCustomerVo> queryVo(QueryObject queryObject);

}
