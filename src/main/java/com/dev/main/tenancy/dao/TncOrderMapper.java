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

    Long getCarId(Long itemId);
    int updateCarNubDown(Long carid);
    int updateCarNubUp(Long carid);


    StoreVo getStore(Long id);
    CarVo getCar(Long id);
    String getCarPic(Long carId);
    List<OrderListVo> getOrderList(QueryObject queryObject);
    List<OrderListVo> getOrderListByWord(QueryObject queryObject);

    List<String> getCarNub(Long carId);

    //根据车牌号修改车item的状态
    int updateCarNub(String carNub1, Byte status);

    //根据车牌号获取车itemid
    Long getCarItemIdByNub(String carNubBefore);

    //更改order对应的caritemid
    int updateCarItemId(Long orderId, Long newItemId);
}
