package com.dev.main.tenancy.vo;

import com.dev.main.tenancy.domain.TncBrand;
import com.dev.main.tenancy.domain.TncCar;
import com.dev.main.tenancy.domain.TncCarItem;
import com.dev.main.tenancy.domain.TncStore;

import java.math.BigDecimal;

public class TncOrderVo_2 {
    // 主键
    private Long id;

    // 姓名
    private String name;

    // 手机号
    private String phone;

    // 车item 外键
    private Long carItemId;
    // 车item 外键(车牌号、车型号名、车品牌名)
    private TncCarItem carItem;

    private Long carId;
    private TncCar car;

    private Long brandId;
    private TncBrand band;

    // 取车门店
    private Long getStoreId;

    // 还车门店
    private Long returnStoreId;

    // 总价 = (下单时)订单价格 + 其它费用
    private BigDecimal totalAmount;

    // 取车门店
    private TncStore getStore;

    // 还车门店
    private TncStore returnStore;

    // 状态：0-等待付款、1-租赁中、2-预定成功、3-已取消、4-已完成、5-处理中
    private Byte status;

    public TncOrderVo_2() {
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getCarItemId() {
        return carItemId;
    }

    public void setCarItemId(Long carItemId) {
        this.carItemId = carItemId;
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

    public TncStore getGetStore() {
        return getStore;
    }

    public void setGetStore(TncStore getStore) {
        this.getStore = getStore;
    }

    public TncStore getReturnStore() {
        return returnStore;
    }

    public void setReturnStore(TncStore returnStore) {
        this.returnStore = returnStore;
    }

    public TncCarItem getCarItem() {
        return carItem;
    }

    public void setCarItem(TncCarItem carItem) {
        this.carItem = carItem;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

}
