package com.dev.main.echarts.util;

import com.dev.main.echarts.statics.EChartConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EChartObject extends HashMap<String, Object> {

    public EChartObject() {
    }

    /**
     * 创建仪表盘
     * @return
     * @param text 仪表盘文本名称
     * @param fontSize 字体大小
     * @param value 仪表盘的值
     */
    public static EChartObject createGauge(String text,int fontSize,String value){
        return null;
    }
    /**
     * 创建二维坐标系图
     * @return
     * @param text 标题
     * @param xAxisName x轴名称
     * @param y1AxisName y1轴名称
     * @param y2AxisName y2轴名称
     * @param y1AxisUnit 单位 如"%"、"℃"之类
     * @param y2AxisUnit 单位 如"%"、"℃"之类
     * @param xData x轴值
     * @param series series
     *          series数据格式:
     *
     *          key   = ("name"  | "yAxisIndex" | "data" | "type" | "label")
     *
     *          value = (数据名  | y轴索引(0|1)  | 数据   | 类型    | 标签配置)
     */
    public static EChartObject createChart(String text,
                                         String xAxisName, List<String> xData,
                                         String y1AxisName, String y2AxisName,
                                         String y1AxisUnit, String y2AxisUnit,
                                         List<Map<String, Object>> series){
        EChartObject chart = new EChartObject();
        // 设置标题
        Map<String, String> title = new HashMap<>();
        title.put(EChartConstant.TEXT, text);
        chart.put(EChartConstant.TITLE, title);

        // 设置tooltip
        Map<String, String> tooltip = new HashMap<>();
        tooltip.put(EChartConstant.TRIGGER, EChartConstant.ITEM);
        chart.put(EChartConstant.TOOLTIP, tooltip);

        // 设置x轴
        Map<String, Object> xAxis = new HashMap<>();
        xAxis.put(EChartConstant.TYPE, EChartConstant.CATEGORY);
        xAxis.put(EChartConstant.DATA, xData);
        xAxis.put(EChartConstant.NAME, xAxisName);

        Map<String, Object> axisLabel = new HashMap<>();
        axisLabel.put(EChartConstant.INTERVAL, 0);
        axisLabel.put(EChartConstant.ROTATE, 45);
        xAxis.put(EChartConstant.AXIS_LABEL, axisLabel);
        chart.put(EChartConstant.X_AXIS, xAxis);

        // 设置y轴
        int yAxisNum = StringUtils.isBlank(y2AxisName) ? 1:2; // 判断轴数
        Object[] yAxises = new Object[yAxisNum];

        Map<String, Object> y1Axis = new HashMap<>();
        y1Axis.put(EChartConstant.TYPE, EChartConstant.VALUE);
        y1Axis.put(EChartConstant.NAME, y1AxisName + '/' + y1AxisUnit);
        yAxises[0] = y1Axis;

        if (yAxisNum == 2) {
            Map<String, Object> y2Axis = new HashMap<>();
            y2Axis.put(EChartConstant.TYPE, EChartConstant.VALUE);
            y2Axis.put(EChartConstant.NAME, y2AxisName + '/' + y2AxisUnit);
            yAxises[1] = y2Axis;
        }

        chart.put(EChartConstant.Y_AXIS, yAxises);

        // 设置legend
        List<String> legendData = new ArrayList<>();
        for (int i = 0; i < series.size(); i++) {
            legendData.add((String)series.get(i).get(EChartConstant.NAME));
        }
        HashMap<String, Object> legend = new HashMap<>();
        legend.put(EChartConstant.DATA, legendData);
        if (series.size() > 1) {  // 数据项大于1时才设置Legend
            chart.put(EChartConstant.LEGEND, legend);
        }
        chart.put(EChartConstant.SERIES, series);
        return chart;
    }


    /**
     * 创建饼图
     * @param text 标题
     * @param fontSize 字体大小
     * @param x 图例水平安放位置，默认为全图居中，可选为：'center' | 'left' | 'right'
     * @param y 图例垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center'
     * @param radius 半径 格式50%
     * @param data 饼图数据
     *
     */
    public static EChartObject createPie(String text,int fontSize,String x,String y,
                                       String radius,List<Object> data){
        return null;
    }
    /**
     * 创建环形图
     * @param text 标题
     * @param fontSize 字体大小
     * @param x 图例水平安放位置，默认为全图居中，可选为：'center' | 'left' | 'right'
     * @param y 图例垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center'
     * @param innerRadius 内半径，格式20%
     * @param outerRadius 外半径，格式30%
     * @param data 环形图数据
     */
    public static EChartObject createRing(String text,int fontSize,String x,String y,
                                        String innerRadius,String outerRadius,List<Object> data){
        return null;
    }
    /**
     * 饼图和环形图生成json
     * @param text 标题
     * @param fontSize 字体大小
     * @param x 图例水平安放位置，默认为全图居中，可选为：'center' | 'left' | 'right'
     * @param y 图例垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center'
     * @param radiusArray 半径数组，[内半径，外半径] 格式{"50%", "70%"}
     */
    private static EChartObject createPieAndRingJson(String text,int fontSize,String x,String y,
                                                   String radiusArray[],List<Object> data){
        return null;
    }

    /**
     * 标准面积图/堆积面积图
     * @param text 标题
     * @param fontSize
     * @param xAxisName x轴名称
     * @param yAxisName y轴名称
     * @param xData x轴值
     * @param unit 单位 如"%"、"℃"之类
     * @param dataList 数据
     * @return
     */
    public static EChartObject createAreaStack(String text,int fontSize,String xAxisName,String xData[],
                                             String yAxisName,String unit,List<Object> dataList){
        return null;
    }

    /**
     * 温度计式图表
     * @param text 标题
     * @param fontSize
     * @param xAxisName x轴名称
     * @param yAxisName y轴名称
     * @param xData x轴值
     * @param unit 单位 如"%"、"℃"之类
     * @param thermometerList 数据
     * @return
     */
    public static EChartObject createTermometer(String text,int fontSize,String xAxisName,String xData[],
                                              String yAxisName,String unit,List<Object> thermometerList){
        return null;
    }
    /**
     * 创建漏斗图
     * @return
     * @param text 标题
     * @param fontSize 字体大小
     * @param dataList 漏斗图的值
     */
    public static EChartObject createFunnel(String text,int fontSize,List<Object> dataList){
        return null;
    }
    /**
     * 创建玫瑰图
     * @return
     * @param text 标题
     * @param fontSize 字体大小
     *
     */
    public static EChartObject createRose(String text,int fontSize,
                                        List<Object> data){
        return null;
    }
    /**
     * 创建中国地图
     * @return
     * @param text 柱状图标题
     * @param fontSize 字体大小
     *
     */
    public static EChartObject createChinaMap(String text,int fontSize,List<Object> data){
        return null;
    }

}
