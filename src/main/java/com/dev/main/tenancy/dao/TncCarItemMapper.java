package com.dev.main.tenancy.dao;

import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.domain.TncCarItem;
import org.apache.ibatis.annotations.Param;

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

    int updateCarItemStatus(@Param("id") Integer id, @Param("status")Byte status);


    List<TncCarItem> getCarItemList(QueryObject queryObject);

    int quantityAndResidualPlusOne(Long carId);

    TncCarItem checkRepetive(String number);

    int quantitySubOne(Integer id);

    int residualAddOne(Integer id);

    int residualSubOne(Integer id);

    int quantityAndResidualSubOne(Integer id);
}
