package com.dev.main.tenancy.controller;

import com.dev.main.common.controller.exception.GlobalExceptionResolver;
import com.dev.main.common.util.DateUtils;
import com.dev.main.common.util.ResultMap;
import com.dev.main.echarts.util.EChartObject;
import com.dev.main.tenancy.service.IEChartService;
import com.dev.main.tenancy.vo.SalesSituationVo;
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
    @GetMapping("/topSalesAmount")
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
    @GetMapping("/topSalesVolume")
    public ResultMap topIncomesCars(String startDate, String endDate, Integer x) {
        EChartObject option = chartService.topSalesAmountCars(DateUtils.getDateByStr(startDate), DateUtils.getDateByStr(endDate), x);
        return ResultMap.success().put("option", option);
    }

    /**
     *
     * 统计某个时段的销售情况
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    @GetMapping("/totalSalesSituation")
    public ResultMap topRent(String startDate, String endDate) {
        SalesSituationVo result = chartService.countSituation(DateUtils.getDateByStr(startDate), DateUtils.getDateByStr(endDate));
        return ResultMap.success().put("data", result);
    }

    /**
     *
     * 统计当天的销售情况
     */
    @GetMapping("/todaySalesSituation")
    public ResultMap todaySituation() {
        SalesSituationVo result = chartService.countSituation(new Date(), new Date());
        return ResultMap.success().put("data", result);
    }

    /**
     *
     * 统计某时间段内的汽车销量及销售额
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    @GetMapping("sellSituation")
    public ResultMap sellSituation(String startDate, String endDate) {
        EChartObject option = chartService.sellSituation(DateUtils.getDateByStr(startDate), DateUtils.getDateByStr(endDate));
        return ResultMap.success().put("option", option);
    }

    public void setChartService(IEChartService chartService) {
        this.chartService = chartService;
    }
}
