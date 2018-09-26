package com.dev.main.tenancy.service;

import com.dev.main.echarts.util.EChartObject;
import com.dev.main.tenancy.vo.CarRentIncomeVo;
import com.dev.main.tenancy.vo.SalesSituationVo;

import java.util.Date;

public interface IEChartService {

    /**
     *
     * 统计某个时段销量最高的x辆车
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param x 车数量
     */
    public EChartObject topRentCars(Date startDate, Date endDate, Integer x);

    /**
     *
     * 统计某个时段销售额最高的x辆车
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param x 车数量
     */
    public EChartObject topSalesAmountCars(Date startDate, Date endDate, Integer x);

    /**
     *
     * 统计某时间段内的汽车销量及销售额，每天一条记录
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    public EChartObject sellSituation(Date startDate, Date endDate);

    /**
     * 统计某时间段内的汽车销量及销售额, 合计结果
     * @return
     */
    public SalesSituationVo countSituation(Date startDate, Date endDate);

}
