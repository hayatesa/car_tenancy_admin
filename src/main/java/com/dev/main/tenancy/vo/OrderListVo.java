package com.dev.main.tenancy.vo;

import java.math.BigDecimal;

public class OrderListVo {
    private Long id;
    private String name;
    private String phone;
    private Long carId;
    private Byte status;
    private Long getStoreId;
    private Long returnStoreId;
    private BigDecimal totalAmount;

    private CarVo carVo;
    private StoreVo getStoreVo;
    private StoreVo returnStoreVo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getGetStoreId() {
        return getStoreId;
    }

    public void setGetStoreId(Long getStoreId) {
        this.getStoreId = getStoreId;
    }

    public Long getReturnStoreId() {
        return returnStoreId;
    }

    public void setReturnStoreId(Long returnStoreId) {
        this.returnStoreId = returnStoreId;
    }

    public CarVo getCarVo() {
        return carVo;
    }

    public void setCarVo(CarVo carVo) {
        this.carVo = carVo;
    }

    public StoreVo getGetStoreVo() {
        return getStoreVo;
    }

    public void setGetStoreVo(StoreVo getStoreVo) {
        this.getStoreVo = getStoreVo;
    }

    public StoreVo getReturnStoreVo() {
        return returnStoreVo;
    }

    public void setReturnStoreVo(StoreVo returnStoreVo) {
        this.returnStoreVo = returnStoreVo;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "OrderListVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", carId=" + carId +
                ", status=" + status +
                ", getStoreId=" + getStoreId +
                ", returnStoreId=" + returnStoreId +
                ", totalAmount=" + totalAmount +
                ", carVo=" + carVo +
                ", getStoreVo=" + getStoreVo +
                ", returnStoreVo=" + returnStoreVo +
                '}';
    }
}
