package com.dev.main.tenancy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dev.main.common.statics.StatusCode;
import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.dao.TncPackageSchemeMapper;
import com.dev.main.tenancy.domain.TncPackageScheme;
import com.dev.main.tenancy.service.IPackageSchemeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PackageSchemeServiceImpl implements IPackageSchemeService {

    @Autowired
    private TncPackageSchemeMapper tncPackageSchemeMapper;

    @Override
    public Page listPackageScheme(QueryObject queryObject) {
        PageHelper.startPage((int) queryObject.get("page"), (int)queryObject.get("limit"), true);
        List<TncPackageScheme> list = tncPackageSchemeMapper.query(queryObject);
        PageInfo pageInfo = new PageInfo(list);
        return new Page(pageInfo.getTotal(), list);
    }

    @Override
    public ResultMap deleteByPrimaryKey(Long id) {
        int res = tncPackageSchemeMapper.deleteByPrimaryKey(id);
        ResultMap resultMap = new ResultMap();
        if(res > 0){
            resultMap.put("msg","删除成功");
        }else{
            resultMap.put("msg","删除失败");
        }
        return resultMap;

    }

    @Override
    public ResultMap insert(TncPackageScheme tps) {
        int res = tncPackageSchemeMapper.insert(tps);
        ResultMap resultMap = new ResultMap();
        if(res > 0 ){
            resultMap.put("msg","添加成功");
        }else{
            resultMap.put("msg","添加失败");
        }
        return resultMap;
    }

    @Override
    public int insertSelective(TncPackageScheme record) {
        return tncPackageSchemeMapper.insertSelective(record);
    }

    @Override
    public TncPackageScheme selectByPrimaryKey(Long id) {
        return tncPackageSchemeMapper.selectByPrimaryKey(id);
    }

    @Override
    public ResultMap updateByPrimaryKeySelective(TncPackageScheme record) {
        int res = tncPackageSchemeMapper.updateByPrimaryKeySelective(record);
        ResultMap resultMap = new ResultMap();
        if(res > 0 ){
            resultMap.put("msg","编辑成功");
        }else{
            resultMap.put("msg","编辑失败");
        }
        return resultMap;
    }

    @Override
    public int updateByPrimaryKey(TncPackageScheme record) {
        return tncPackageSchemeMapper.updateByPrimaryKey(record);
    }

    @Override
    public ResultMap selectPackageSchemeNmae(TncPackageScheme record) {
        TncPackageScheme res = tncPackageSchemeMapper.selectPackageSchemeByNmae(record);
        ResultMap resultMap = new ResultMap();
        if ( res != null ){
            resultMap.put("msg","已存在");
        } else {
            resultMap.put("msg","可用");
        }
        return resultMap;
    }

    @Override
    public List<TncPackageScheme> getUnSelectPackage(byte carId) {

        return tncPackageSchemeMapper.getUnSelectPackage(carId);
    }
}
