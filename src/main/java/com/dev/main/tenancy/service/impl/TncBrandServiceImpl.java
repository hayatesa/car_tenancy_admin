package com.dev.main.tenancy.service.impl;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.dao.TncBrandMapper;
import com.dev.main.tenancy.domain.TncBrand;
import com.dev.main.tenancy.service.ITncBrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TncBrandServiceImpl implements ITncBrandService {

    @Autowired
    private TncBrandMapper tncBrandMapper;

    @Override
    public int deleteBrand(Long id) {
        return tncBrandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int addBrand(TncBrand record) {
        return tncBrandMapper.insert(record);
    }

    @Override
    public TncBrand findByPrimaryKey(Long id) {
        return tncBrandMapper.selectByPrimaryKey(id);
    }

    @Override
    public int modifiedByPrimaryKeySelective(TncBrand record) {
        return tncBrandMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Page queryByPage(QueryObject queryObject) {
        PageHelper.startPage((int) queryObject.get("page"), (int)queryObject.get("limit"), true);
        List<TncBrand> tncBrandList = tncBrandMapper.query(queryObject);
        PageInfo pageInfo = new PageInfo(tncBrandList);
        return new Page(pageInfo.getTotal(), tncBrandList);
    }

    @Override
    public TncBrand findByName(TncBrand record) {
        return tncBrandMapper.selectByName(record);
    }

    @Override
    public List<TncBrand> getBandList() {
        return tncBrandMapper.selectAllBand();
    }
}
