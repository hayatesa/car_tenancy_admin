package com.dev.main.tenancy.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dev.main.tenancy.dao.TncPriceSchemeMapper;
import com.dev.main.tenancy.domain.TncPriceScheme;
import com.dev.main.tenancy.service.ITncPriceSchemeService;
import com.dev.main.tenancy.vo.TncCarPriceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TncPriceSchemeServiceImpl implements ITncPriceSchemeService {

    @Autowired
    private TncPriceSchemeMapper tncPriceSchemeMapper;

    @Override
    public int addPriceScheme(TncPriceScheme tncPriceScheme) {
        tncPriceScheme.setIsDeleted(new Byte("0"));
        tncPriceScheme.setGmtCreate(new Date());
        tncPriceScheme.setGmtModified(new Date());
        return tncPriceSchemeMapper.insertSelective(tncPriceScheme);
    }

    @Override
    public List<TncCarPriceVo> selectCarPriceList(Integer carId) {
        return tncPriceSchemeMapper.selectCarPriceList(carId);
    }


    @Override
    public int updatePrice(TncPriceScheme tncPriceScheme) {
        tncPriceScheme.setGmtModified(new Date());
        tncPriceScheme.setGmtCreate(null);
        tncPriceScheme.setIsDeleted(new Byte("0"));
        return tncPriceSchemeMapper.updateByPrimaryKeySelective(tncPriceScheme);
    }

    @Override
    public int deletePrice(Long id) {
        TncPriceScheme tncPriceScheme  = new TncPriceScheme();
        tncPriceScheme.setIsDeleted(new Byte("1"));
        tncPriceScheme.setId(id);
        return tncPriceSchemeMapper.updateByPrimaryKeySelective(tncPriceScheme);
    }


    @Transactional
    @Override
    public int batchDelete(String dataList) {
        JSONArray json = JSONArray.parseArray(dataList);
        for(int i=0;i<json.size();i++){
            JSONObject job = json.getJSONObject(i);
            TncPriceScheme tncPriceScheme = new TncPriceScheme();
            tncPriceScheme.setId(new Long(job.get("id").toString()));
            tncPriceScheme.setIsDeleted(new Byte("1"));
            tncPriceScheme.setGmtModified(new Date());
            tncPriceScheme.setGmtCreate(null);
            tncPriceSchemeMapper.updateByPrimaryKeySelective(tncPriceScheme);
        }
        return 0;
    }

}
