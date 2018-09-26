package com.dev.main.tenancy.vo;

import java.math.BigDecimal;

public class SalesSituationVo {

    private Long salesVolume;
    private BigDecimal salesAmount;

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
}
