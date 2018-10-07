package com.dev.main.tenancy.dao;

import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.domain.TncOrder;
import com.dev.main.tenancy.vo.*;

import java.util.List;

public interface TncOrderMapper extends BaseMapper<TncOrder> {
    int deleteByPrimaryKey(Long id);

    int insert(TncOrder record);

    int insertSelective(TncOrder record);

    TncOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TncOrder record);

    int updateByPrimaryKey(TncOrder record);

    List<TncOrderListVo> query2(QueryObject queryObject);

    List<TncOrder> query(QueryObject queryObject);

    List<TncOrderListVo> queryByWord(QueryObject queryObject);


    StoreVo getStore(Long id);
    CarVo getCar(Long id);
    String getCarPic(Long carId);
    List<OrderListVo> getOrderList(QueryObject queryObject);
    List<OrderListVo> getOrderListByWord(QueryObject queryObject);
}
