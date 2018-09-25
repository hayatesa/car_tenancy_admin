package com.dev.main.tenancy.dao;

import com.dev.main.tenancy.vo.CarRentIncomeVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CarRentIncomeMapper {

    /**
     * 查询销量最高的车
     * @param params
     * @return
     */
    List<CarRentIncomeVo> findTopRent(Map<String, Object> params);


    /**
     * 查询销售额最高的车
     * @param params
     * @return
     */
    List<CarRentIncomeVo> findTopSalesAmount(Map<String, Object> params);

    /**
     * 某天销售量和销售额
     * @return
     */
    CarRentIncomeVo findSituationOfOneDay(Date date);

}
