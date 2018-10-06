package com.dev.main.tenancy.dao;

import com.dev.main.tenancy.vo.CarRentIncomeVo;
import com.dev.main.tenancy.vo.DaySalesVo;
import com.dev.main.tenancy.vo.SalesSituationVo;

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
     * 统计一段时间内的销量
     * @param params
     * @return
     */
    List<DaySalesVo> getDaySales(Map<String, Object> params);

    /**
     * 统计一段时间内的销量及销售情况
     * @param params
     * @return
     */
    SalesSituationVo getSalesSituation(Map<String, Object> params);

}
