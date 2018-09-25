package com.dev.main.tenancy.service;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.domain.AddressRegion;

import java.util.List;

public interface IRegionService {

    /**
     * 查询一页数据
     * @param queryObject
     * @return
     */
    Page queryByPage(QueryObject queryObject);

    /**查询下级所有地址*/
    List<AddressRegion> findAddress(Long aid, byte level);

    /*查询所有省*/
    List<AddressRegion> getProvince();
}
