package com.dev.main.tenancy.vo;

import com.dev.main.tenancy.domain.*;

import java.math.BigDecimal;

public class TncOrderVo {
    private  TncOrder tncOrder;
    private TncCustomer tncCustomer;
    private String picPath;
    private String carNub;
    private String carName;
    private String carSeries;

    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public TncCustomer getTncCustomer() {
        return tncCustomer;
    }

    public void setTncCustomer(TncCustomer tncCustomer) {
        this.tncCustomer = tncCustomer;
    }

    public TncOrder getTncOrder() {
        return tncOrder;
    }

    public void setTncOrder(TncOrder tncOrder) {
        this.tncOrder = tncOrder;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getCarNub() {
        return carNub;
    }

    public void setCarNub(String carNub) {
        this.carNub = carNub;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarSeries() {
        return carSeries;
    }

    public void setCarSeries(String carSeries) {
        this.carSeries = carSeries;
    }

    @Override
    public String toString() {
        return "TncOrderVo{" +
                "tncOrder=" + tncOrder +
                ", tncCustomer=" + tncCustomer +
                ", picPath='" + picPath + '\'' +
                ", carNub='" + carNub + '\'' +
                ", carName='" + carName + '\'' +
                ", carSeries='" + carSeries + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
