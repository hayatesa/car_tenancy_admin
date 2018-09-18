package com.dev.main.tenancy.domain;

import com.dev.main.common.domain.BaseDomain;

import java.math.BigDecimal;
import java.util.Date;

public class TncAddress extends BaseDomain {
    // 主键
    private Long id;

    // 省 外键
    private AddressRegion province;

    // 市 外键
    private AddressRegion city;

    // 区 外键
    private AddressRegion area;

    // 地址 详细地址
    private String detail;

    // 地址类型 0-门店地址 1-用户地址
    private Byte storeOrUser;

    // 经度
    private BigDecimal longitude;

    // 纬度
    private BigDecimal latitude;

    // 创建时间
    private Date gmtCreate;

    // 修改时间
    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressRegion getProvince() {
        return province;
    }

    public void setProvince(AddressRegion province) {
        this.province = province;
    }

    public AddressRegion getCity() {
        return city;
    }

    public void setCity(AddressRegion city) {
        this.city = city;
    }

    public AddressRegion getArea() {
        return area;
    }

    public void setArea(AddressRegion area) {
        this.area = area;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public Byte getStoreOrUser() {
        return storeOrUser;
    }

    public void setStoreOrUser(Byte storeOrUser) {
        this.storeOrUser = storeOrUser;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
