package com.dev.main.tenancy.service;

import com.dev.main.echarts.util.EChartObject;

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
     * 统计某个时段的总销售额
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    public EChartObject topIncomes(Date startDate, Date endDate) ;

    /**
     *
     * 统计某个时段的总销售量
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    public EChartObject topRent(Date startDate, Date endDate);

    /**
     *
     * 统计当天的总销售量
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    public EChartObject todayRent(Date startDate, Date endDate);

    /**
     *
     * 统计当天的总销售额
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    public EChartObject todayIncomes(Date startDate, Date endDate);

    /**
     *
     * 统计某时间段内的汽车销量及销售额
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    public EChartObject sellSituation(Date startDate, Date endDate);

}
