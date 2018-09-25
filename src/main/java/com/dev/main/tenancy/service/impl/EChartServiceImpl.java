package com.dev.main.tenancy.service.impl;

import com.dev.main.echarts.statics.EChartConstant;
import com.dev.main.tenancy.dao.CarRentIncomeMapper;
import com.dev.main.tenancy.service.IEChartService;
import com.dev.main.echarts.util.EChartObject;
import com.dev.main.tenancy.vo.CarRentIncomeVo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class EChartServiceImpl implements IEChartService {

    @Autowired
    private CarRentIncomeMapper carRentIncomeMapper;


    @Override
    public EChartObject topRentCars(Date startDate, Date endDate, Integer x) {
        Map<String, Object> param = new HashMap<>();
        param.put("startDate", startDate);
        param.put("endDate", DateUtils.addDays(endDate, 1)); // 添加一天，即第二天零点
        param.put("x", x);
        List<CarRentIncomeVo> datas = carRentIncomeMapper.findTopRent(param);

        String text = "销量TOP" + x;
        String xAxisName = "";
        List<String> xData = new ArrayList<>();

        List<Long> volumeData = new ArrayList<>(); // 销量数据
        List<BigDecimal> amountData = new ArrayList<>(); // 销售额数据
        for (int i = 0; i < datas.size(); i++) {
            xData.add(datas.get(i).getCarName());
            volumeData.add(datas.get(i).getSalesVolume());
            amountData.add(datas.get(i).getSalesAmount());
        }
        String y1AxisName = "销量";
        String y1Unit = "辆次";
        String y2AxisName = "销售额";
        String y2Unit = "元";

        List<Map<String, Object>> series = new ArrayList<>();
        Map<String, Object> volumeSeries = new HashMap<>();
        volumeSeries.put(EChartConstant.NAME, y1AxisName);
        volumeSeries.put(EChartConstant.Y_AXIS_INDEX, 0);
        volumeSeries.put(EChartConstant.DATA, volumeData);
        volumeSeries.put(EChartConstant.TYPE, EChartConstant.BAR);
        Map<String, Object> amountSeries = new HashMap<>();
        amountSeries.put(EChartConstant.NAME, y2AxisName);
        amountSeries.put(EChartConstant.Y_AXIS_INDEX, 1);
        amountSeries.put(EChartConstant.DATA, amountData);
        amountSeries.put(EChartConstant.TYPE, EChartConstant.BAR);
        series.add(volumeSeries);
        series.add(amountSeries);
        EChartObject chart = EChartObject.createChart(text, xAxisName, xData, y1AxisName, y2AxisName, y1Unit, y2Unit, series);

        return chart;
    }

    @Override
    public EChartObject topSalesAmountCars(Date startDate, Date endDate, Integer x) {
        Map<String, Object> param = new HashMap<>();
        param.put("startDate", startDate);
        param.put("endDate", DateUtils.addDays(endDate, 1)); // 添加一天，即第二天零点
        param.put("x", x);
        List<CarRentIncomeVo> datas = carRentIncomeMapper.findTopSalesAmount(param);

        String text = "销售额TOP" + x;
        String xAxisName = "";
        List<String> xData = new ArrayList<>();

        List<Long> volumeData = new ArrayList<>(); // 销量数据
        List<BigDecimal> amountData = new ArrayList<>(); // 销售额数据
        for (int i = 0; i < datas.size(); i++) {
            xData.add(datas.get(i).getCarName());
            volumeData.add(datas.get(i).getSalesVolume());
            amountData.add(datas.get(i).getSalesAmount());
        }
        String y1AxisName = "销售额";
        String y1Unit = "元";
        String y2AxisName = "销量";
        String y2Unit = "辆次";

        List<Map<String, Object>> series = new ArrayList<>();
        Map<String, Object> amountSeries = new HashMap<>();
        amountSeries.put(EChartConstant.NAME, y1AxisName);
        amountSeries.put(EChartConstant.Y_AXIS_INDEX, 0);
        amountSeries.put(EChartConstant.DATA, amountData);
        amountSeries.put(EChartConstant.TYPE, EChartConstant.BAR);
        Map<String, Object> volumeSeries = new HashMap<>();
        volumeSeries.put(EChartConstant.NAME, y2AxisName);
        volumeSeries.put(EChartConstant.Y_AXIS_INDEX, 1);
        volumeSeries.put(EChartConstant.DATA, volumeData);
        volumeSeries.put(EChartConstant.TYPE, EChartConstant.BAR);
        series.add(amountSeries);
        series.add(volumeSeries);
        EChartObject chart = EChartObject.createChart(text, xAxisName, xData, y1AxisName, y2AxisName, y1Unit, y2Unit, series);

        return chart;
    }

    @Override
    public EChartObject topIncomes(Date startDate, Date endDate) {
        return null;
    }

    @Override
    public EChartObject topRent(Date startDate, Date endDate) {
        return null;
    }

    @Override
    public EChartObject todayRent(Date startDate, Date endDate) {
        return null;
    }

    @Override
    public EChartObject todayIncomes(Date startDate, Date endDate) {
        return null;
    }

    @Override
    public EChartObject sellSituation(Date startDate, Date endDate) {
        return null;
    }

    public void setCarRentIncomeMapper(CarRentIncomeMapper carRentIncomeMapper) {
        this.carRentIncomeMapper = carRentIncomeMapper;
    }
}
