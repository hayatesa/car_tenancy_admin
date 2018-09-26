package com.dev.main.tenancy.service.impl;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.dao.TncStoreMapper;
import com.dev.main.tenancy.domain.TncStore;
import com.dev.main.tenancy.service.ITncStoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TncStoreServiceImpl implements ITncStoreService {

    @Autowired
    private TncStoreMapper tncStoreMapper;


    @Override
    public int deleteStore(Long id) {
        return tncStoreMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int addStore(TncStore record) {
        return tncStoreMapper.insert(record);
    }

    @Override
    public TncStore findByPrimaryKey(Long id) {
        return tncStoreMapper.selectByPrimaryKey(id);
    }

    @Override
    public int modifiedByPrimaryKeySelective(TncStore record) {
        return tncStoreMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Page queryByPage(QueryObject queryObject) {
        PageHelper.startPage((int) queryObject.get("page"), (int) queryObject.get("limit"), true);
        List<TncStore> tncStoreList = tncStoreMapper.query(queryObject);
        PageInfo pageInfo = new PageInfo(tncStoreList);
        return new Page(pageInfo.getTotal(), tncStoreList);
    }

    @Override
    public TncStore findByName(TncStore record) {
        return null;
    }

    @Override
    public List<TncStore> searchStoreList(Integer areaId) {
        return tncStoreMapper.selectStoreByAreaId(areaId);
    }
}
