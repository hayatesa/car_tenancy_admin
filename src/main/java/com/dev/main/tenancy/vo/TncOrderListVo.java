package com.dev.main.tenancy.vo;

import com.dev.main.tenancy.domain.TncCarItem;
import com.dev.main.tenancy.domain.TncStore;

import java.math.BigDecimal;

public class TncOrderListVo {
    // 主键
    private Long id;
    // 姓名
    private String name;
    // 手机号
    private String phone;
    // 取车门店
    private Long getStoreId;
    private StoreVo getStore;

    // 还车门店
    private Long returnStoreId;
    private StoreVo returnStore;

    // 总价 = (下单时)订单价格 + 其它费用
    private BigDecimal totalAmount;

    // 状态：0-提交订单 1-失效 2-已支付 3-用户取消（退款） 4-完成
    private Byte status;
    // 车item 外键
    private Long carItemId;
    private CarVo carItem;

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

    public Long getGetStoreId() {
        return getStoreId;
    }

    public void setGetStoreId(Long getStoreId) {
        this.getStoreId = getStoreId;
    }

    public StoreVo getGetStore() {
        return getStore;
    }

    public void setGetStore(StoreVo getStore) {
        this.getStore = getStore;
    }

    public Long getReturnStoreId() {
        return returnStoreId;
    }

    public void setReturnStoreId(Long returnStoreId) {
        this.returnStoreId = returnStoreId;
    }

    public StoreVo getReturnStore() {
        return returnStore;
    }

    public void setReturnStore(StoreVo returnStore) {
        this.returnStore = returnStore;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getCarItemId() {
        return carItemId;
    }

    public void setCarItemId(Long carItemId) {
        this.carItemId = carItemId;
    }

    public CarVo getCarItem() {
        return carItem;
    }

    public void setCarItem(CarVo carItem) {
        this.carItem = carItem;
    }

    @Override
    public String toString() {
        return "TncOrderListVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", getStoreId=" + getStoreId +
                ", getStore=" + getStore +
                ", returnStoreId=" + returnStoreId +
                ", returnStore=" + returnStore +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", carItemId=" + carItemId +
                ", carItem=" + carItem +
                '}';
    }
}
