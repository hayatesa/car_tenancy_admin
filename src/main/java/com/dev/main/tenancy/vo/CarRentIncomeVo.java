package com.dev.main.tenancy.vo;

import java.math.BigDecimal;

public class CarRentIncomeVo {

    private Long carId; // 车id
    private String carName; // 车名称
    private Long salesVolume; // 销量
    private BigDecimal salesAmount; // 销售额

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public Long getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Long salesVolume) {
        this.salesVolume = salesVolume;
    }

    public BigDecimal getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(BigDecimal salesAmount) {
        this.salesAmount = salesAmount;
    }

    @Override
    public String toString() {
        return "CarRentIncomeVo{" +
                "carId=" + carId +
                ", carName='" + carName + '\'' +
                ", salesVolume=" + salesVolume +
                ", salesAmount=" + salesAmount +
                '}';
    }
}
