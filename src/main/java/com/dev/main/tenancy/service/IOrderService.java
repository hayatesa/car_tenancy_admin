package com.dev.main.tenancy.service;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.domain.TncCustomer;
import com.dev.main.tenancy.domain.TncOrder;
import com.dev.main.tenancy.vo.TncCustomerVo;
import com.dev.main.tenancy.vo.TncOrderVo;

import java.util.List;

public interface IOrderService {

    Page queryByPage(QueryObject queryObject);

    Page queryByPage_Word(QueryObject queryObject);

    ResultMap findByPrimaryKey(Long id);

    int modifiedByPrimaryKeySelective(TncOrder tncOrder);

    int update(TncOrder tncOrder);

    TncCustomerVo findUser(Long phone);

    int delete(Long id);

    ResultMap selectCarId(Long carId);

    int updateCarNub(String carNubBefore, String carNubNew);

    int updateCarItemId(Long orderId, String carNubBefore, String carNubNew);

    int updateUser(TncCustomer customer);
}
