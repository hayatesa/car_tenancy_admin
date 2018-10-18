package com.dev.main.tenancy.service;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.domain.TncCarItem;

public interface ICarItemService {
    Page getCarItemList(QueryObject queryObject);

    int addCarItem(TncCarItem tncCarItem);

    int deleteCarItem(TncCarItem id);

    int updateCarItemStatus(Integer id, Byte status);

    int updateCarItem(TncCarItem tncCarItem);

    Page findCarItemListBySearch(QueryObject queryObject);

    int batchShelves(String dataList);

    int repairCarItemList(String dataList);

    int quantityAndResidualPlusOne(Long carId);

    int quantitySubOne(Integer id);

    int residualAddOne(Integer id);

    int residualSubOne(Integer id);

    int quantityAndResidualSubOne(Integer id);

    TncCarItem checkRepetive(String number);

    int updateCarItemStatusSubQ(Integer id, Byte status);

    int deleteCarItemSubQ(TncCarItem tncCarItem);
}
