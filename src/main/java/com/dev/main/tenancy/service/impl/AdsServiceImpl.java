package com.dev.main.tenancy.service.impl;

import com.dev.main.common.exception.CommonException;
import com.dev.main.common.statics.StatusCode;
import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.dao.TncAdsMapper;
import com.dev.main.tenancy.domain.TncAds;
import com.dev.main.tenancy.service.IAdsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *  * Description: catr_tenancy_admin
 *  * Created by sf on 2018/9/14 9:11
 *  
 */
@Service
public class AdsServiceImpl implements IAdsService {

    @Autowired
    public TncAdsMapper tncAdsMapper;
    @Override
    public Page queryByPage(QueryObject queryObject) {
        PageHelper.startPage((int)queryObject.get("page"),(int)queryObject.get("limit"),true);
        List<TncAds> list = tncAdsMapper.query(queryObject);
        if (list.size()>0){
            PageInfo pageInfo = new PageInfo(list);
            return new Page(pageInfo.getTotal(),list);
        }else{
            throw new CommonException("无广告数据");
        }
    }
    @Override
    public ResultMap addAds(TncAds tncAds){
        ResultMap result = new ResultMap();
        Byte isdelete = 0;
        tncAds.setIsDeleted(isdelete);
        tncAds.setGmtCreate(new Date());
        tncAds.setGmtModified(new Date());
        int res = tncAdsMapper.insert(tncAds);
        if(res>0){
            result.put("msg","添加成功");
            result.put("code", StatusCode.SUCCESS);
        }else{
            throw new CommonException("添加失败");
        }
        return result;
    }

    @Override
    public ResultMap updateAds(TncAds tncAds) {
        ResultMap result = new ResultMap();
        tncAds.setGmtModified(new Date());
        int res = tncAdsMapper.updateByPrimaryKeySelective(tncAds);
        if(res>0){
            result.put("msg","修改成功");
            result.put("code", StatusCode.SUCCESS);
        }else{
            throw new CommonException("修改失败");
        }
        return result;
    }

    @Override
    public ResultMap deleteAds(TncAds tncAds) {
        ResultMap result = new ResultMap();
        Byte isdelete = 1;
        tncAds.setIsDeleted(isdelete);
        tncAds.setGmtModified(new Date());
        int res = tncAdsMapper.updateByPrimaryKeySelective(tncAds);
        if(res>0){
            result.put("msg","删除成功");
            result.put("code", StatusCode.SUCCESS);
        }else{
            throw new CommonException("删除失败");
        }
        return result;
    }

    @Override
    public ResultMap selectById(Long id) {
        TncAds tncAds = tncAdsMapper.selectByPrimaryKey(id);
        if (tncAds != null){
            return ResultMap.success("查询记录成功").put("ad",tncAds);
        }else{
            throw new CommonException("订单查询失败");
        }
    }
}
