package com.dev.main.tenancy.service.impl;

import com.dev.main.common.statics.StatusCode;
import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.controller.exception.SystemErrorException;
import com.dev.main.tenancy.dao.TncAdsMapper;
import com.dev.main.tenancy.domain.TncAds;
import com.dev.main.tenancy.service.IAdsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
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

    private String sys = System.getProperty("os.name");
    private String LINUX_PATH = "/opt/zuche/images";
    private String WIN_PATH = "D:/zuche/images";
    @Autowired
    public TncAdsMapper tncAdsMapper;
    @Override
    public Page queryByPage(QueryObject queryObject) {
        PageHelper.startPage((int)queryObject.get("page"),(int)queryObject.get("limit"),true);
        List<TncAds> list = tncAdsMapper.query(queryObject);
        if (StringUtils.containsIgnoreCase(sys, "linux")) { // Linux系统
            for (TncAds tncAds:list) {
                tncAds.setImagePath(LINUX_PATH+tncAds.getImagePath());
            }
        } else if (StringUtils.containsIgnoreCase(sys, "windows")) { // Windows系统
            for (TncAds tncAds:list) {
                tncAds.setImagePath(WIN_PATH+tncAds.getImagePath());
            }
        } else {
            throw new SystemErrorException("未知服务器类型，操作失败;");
        }
        PageInfo pageInfo = new PageInfo(list);
        return new Page(pageInfo.getTotal(),list);
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
            result.put("msg","添加失败");
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
            result.put("msg","修改失败");
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
            result.put("msg","删除失败");
        }
        return result;
    }
}
