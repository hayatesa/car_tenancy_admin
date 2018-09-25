package com.dev.main.tenancy.service.impl;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.dao.AddressRegionMapper;
import com.dev.main.tenancy.domain.AddressRegion;
import com.dev.main.tenancy.service.IRegionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements IRegionService {

    @Autowired
    private AddressRegionMapper addressRegionMapper;

    @Override
    public Page queryByPage(QueryObject queryObject) {
        PageHelper.startPage((int) queryObject.get("page"), (int)queryObject.get("limit"), true);
        List<AddressRegion> list = addressRegionMapper.query(queryObject);
        PageInfo pageInfo = new PageInfo(list);
        return new Page(pageInfo.getTotal(), list);
    }

    @Override
    public List<AddressRegion> findAddress(Long aid, byte level) {
        List<AddressRegion> addressRegions;
        addressRegions = addressRegionMapper.searchAddress(aid, level);
        return addressRegions;
    }

    @Override
    public List<AddressRegion> getProvince() {

        return addressRegionMapper.selectAddressByLevel(new Byte("0"));
    }

    public void setAddressRegionMapper(AddressRegionMapper addressRegionMapper) {
        this.addressRegionMapper = addressRegionMapper;
    }
}
