package com.dev.main.tenancy.vo;

import com.dev.main.tenancy.domain.TncCar;

import java.math.BigDecimal;

public class TncOrderPriceVo {
    private String returnDate;//还车年月日  -----
    private String returnTime;//还车时分秒  ------
    private String getDate;//取车年月日  -----
    private String getTime;//取车时分秒 -----
    private int days;//租车天数 -----
    private int overtime_count;//超时数  ----
    private int base_price;//基础费（天）----
    private int service_price;//服务费（天）-----
    private BigDecimal total_base_price;//总的基础费  ----
    private BigDecimal total_service_price;//总的服务费  -----
    private BigDecimal discount;//折扣 -----
    private BigDecimal discount_total_base;//打折后的总的基础费  ----
    private BigDecimal discount_total_service;//打折后的总的服务费  -----

    @Override
    public String toString() {
        return "TncOrderPriceVo{" +
                "returnDate='" + returnDate + '\'' +
                ", returnTime='" + returnTime + '\'' +
                ", getDate='" + getDate + '\'' +
                ", getTime='" + getTime + '\'' +
                ", days=" + days +
                ", overtime_count=" + overtime_count +
                ", base_price=" + base_price +
                ", service_price=" + service_price +
                ", total_base_price=" + total_base_price +
                ", total_service_price=" + total_service_price +
                ", discount=" + discount +
                ", discount_total_base=" + discount_total_base +
                ", discount_total_service=" + discount_total_service +
                '}';
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getGetDate() {
        return getDate;
    }

    public void setGetDate(String getDate) {
        this.getDate = getDate;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getOvertime_count() {
        return overtime_count;
    }

    public void setOvertime_count(int overtime_count) {
        this.overtime_count = overtime_count;
    }

    public int getBase_price() {
        return base_price;
    }

    public void setBase_price(int base_price) {
        this.base_price = base_price;
    }

    public int getService_price() {
        return service_price;
    }

    public void setService_price(int service_price) {
        this.service_price = service_price;
    }

    public BigDecimal getTotal_base_price() {
        return total_base_price;
    }

    public void setTotal_base_price(BigDecimal total_base_price) {
        this.total_base_price = total_base_price;
    }

    public BigDecimal getTotal_service_price() {
        return total_service_price;
    }

    public void setTotal_service_price(BigDecimal total_service_price) {
        this.total_service_price = total_service_price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscount_total_base() {
        return discount_total_base;
    }

    public void setDiscount_total_base(BigDecimal discount_total_base) {
        this.discount_total_base = discount_total_base;
    }

    public BigDecimal getDiscount_total_service() {
        return discount_total_service;
    }

    public void setDiscount_total_service(BigDecimal discount_total_service) {
        this.discount_total_service = discount_total_service;
    }
}
