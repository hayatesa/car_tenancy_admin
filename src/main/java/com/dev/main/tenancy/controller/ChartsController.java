package com.dev.main.tenancy.controller;

import com.dev.main.common.controller.exception.GlobalExceptionResolver;
import com.dev.main.common.util.DateUtils;
import com.dev.main.common.util.ResultMap;
import com.dev.main.echarts.util.EChartObject;
import com.dev.main.tenancy.service.IEChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@GlobalExceptionResolver
@RequestMapping("/api/charts")
public class ChartsController {

    @Autowired
    private IEChartService chartService;


    /**
     *
     * 统计某个时段销量最高的x量车
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param x 车数量
     */
    @GetMapping("/topRentCars")
    public ResultMap topRentCars(String startDate, String endDate, Integer x) {
        EChartObject option = chartService.topRentCars(DateUtils.getDateByStr(startDate), DateUtils.getDateByStr(endDate), x);
        return ResultMap.success().put("option", option);
    }

    /**
     *
     * 统计某个时段销售额最高的10量车
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param x 车数量
     */
    @GetMapping("/topIncomesCars")
    public ResultMap topIncomesCars(String startDate, String endDate, Integer x) {
        EChartObject option = chartService.topSalesAmountCars(DateUtils.getDateByStr(startDate), DateUtils.getDateByStr(endDate), x);
        return ResultMap.success().put("option", option);
    }

    /**
     *
     * 统计某个时段的总销售额
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    @GetMapping("/totalIncomes")
    public ResultMap topIncomes(Date startDate, Date endDate) {
        return ResultMap.success();
    }

    /**
     *
     * 统计某个时段的总销售量
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    @GetMapping("/totalRent")
    public ResultMap topRent(Date startDate, Date endDate) {
        return ResultMap.success();
    }

    /**
     *
     * 统计当天的总销售量
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    @GetMapping("/todayRent")
    public ResultMap todayRent(Date startDate, Date endDate) {
        return ResultMap.success();
    }

    /**
     *
     * 统计当天的总销售额
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    @GetMapping("/todayIncomes")
    public ResultMap todayIncomes(Date startDate, Date endDate) {
        return ResultMap.success();
    }

    /**
     *
     * 统计某时间段内的汽车销量及销售额
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    @GetMapping("sellSituation")
    public ResultMap sellSituation(Date startDate, Date endDate) {
        return ResultMap.success();
    }

    public void setChartService(IEChartService chartService) {
        this.chartService = chartService;
    }
}
