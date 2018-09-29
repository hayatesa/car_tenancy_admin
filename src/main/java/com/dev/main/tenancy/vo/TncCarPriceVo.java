package com.dev.main.tenancy.vo;

import com.dev.main.tenancy.domain.TncPackageScheme;
import com.dev.main.tenancy.domain.TncPriceScheme;

public class TncCarPriceVo extends TncPriceScheme {

    //套餐
    private TncPackageScheme tncPackageScheme;

    public TncPackageScheme getTncPackageScheme() {
        return tncPackageScheme;
    }

    public void setTncPackageScheme(TncPackageScheme tncPackageScheme) {
        this.tncPackageScheme = tncPackageScheme;
    }
}
