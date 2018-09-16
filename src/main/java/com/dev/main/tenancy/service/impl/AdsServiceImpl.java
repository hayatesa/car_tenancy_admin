package com.dev.main.tenancy.service.impl;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.dao.TncAdsMapper;
import com.dev.main.tenancy.domain.TncAds;
import com.dev.main.tenancy.service.IAdsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        PageInfo pageInfo = new PageInfo(list);
        return new Page(pageInfo.getTotal(),list);
    }
    @Override
    public void addAds(TncAds tncAds){
        Byte isdelete = 0;
        tncAds.setIsDeleted(isdelete);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = df.parse(df.format(new Date()));
            tncAds.setGmtCreate(date);
            tncAds.setGmtModified(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tncAdsMapper.insert(tncAds);
    }

    @Override
    public void updateAds(TncAds tncAds) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = df.parse(df.format(new Date()));
            tncAds.setGmtModified(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tncAdsMapper.updateByPrimaryKeySelective(tncAds);
    }

    @Override
    public int deleteAds(TncAds tncAds) {
        Byte isdelete = 1;
        tncAds.setIsDeleted(isdelete);
        return tncAdsMapper.updateByPrimaryKeySelective(tncAds);
    }
}
