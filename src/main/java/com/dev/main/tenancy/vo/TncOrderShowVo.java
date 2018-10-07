package com.dev.main.tenancy.vo;

import java.math.BigDecimal;

public class TncOrderShowVo {
    private Long id;
    private String name;
    private String phone;
    private String car_name;
    private String car_series;
    private String car_number;
    private BigDecimal totalAmount;
    private String get_store_name;
    private String return_store_name;
    private Byte status;

    @Override
    public String toString() {
        return "TncOrderShowVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", car_name='" + car_name + '\'' +
                ", car_series='" + car_series + '\'' +
                ", car_number='" + car_number + '\'' +
                ", totalAmount=" + totalAmount +
                ", get_store_name='" + get_store_name + '\'' +
                ", return_store_name='" + return_store_name + '\'' +
                ", status=" + status +
                '}';
    }

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

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCar_series() {
        return car_series;
    }

    public void setCar_series(String car_series) {
        this.car_series = car_series;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getGet_store_name() {
        return get_store_name;
    }

    public void setGet_store_name(String get_store_name) {
        this.get_store_name = get_store_name;
    }

    public String getReturn_store_name() {
        return return_store_name;
    }

    public void setReturn_store_name(String return_store_name) {
        this.return_store_name = return_store_name;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
