package com.dev.main.tenancy.dao;

import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.domain.TncCarItem;

import java.util.List;

public interface TncCarItemMapper extends BaseMapper<TncCarItem> {
    int deleteByPrimaryKey(Long id);

    int insert(TncCarItem record);

    int insertSelective(TncCarItem record);

    TncCarItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TncCarItem record);

    int updateByPrimaryKey(TncCarItem record);

    List<TncCarItem> getCarItemListByCarId(QueryObject queryObject);

    int updateDeleteFieldByPrimaryKey(Integer id);

    int updateCarItemStatus(Integer id, Byte status);

    List<TncCarItem> getCarItemList(QueryObject queryObject);

    int quantityPlusOne(Long carId);
}
