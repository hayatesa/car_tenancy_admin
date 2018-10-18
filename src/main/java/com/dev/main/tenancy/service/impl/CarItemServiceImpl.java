package com.dev.main.tenancy.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.dao.TncCarItemMapper;
import com.dev.main.tenancy.domain.TncCarItem;
import com.dev.main.tenancy.service.ICarItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
public class CarItemServiceImpl implements ICarItemService {
    @Autowired
    private TncCarItemMapper tncCarItemMapper;

    @Override
    public int deleteCarItem(TncCarItem tncCarItem) {
        tncCarItem.setGmtModified(new Date());
        tncCarItem.setIsDeleted(new Byte("1"));
        return tncCarItemMapper.updateDeleteFieldByPrimaryKey(new Integer(tncCarItem.getId().toString()));
    }

    @Override
    public int deleteCarItemSubQ(TncCarItem tncCarItem) {
        tncCarItem.setGmtModified(new Date());
        tncCarItem.setIsDeleted(new Byte("1"));
        return tncCarItemMapper.updateDeleteFieldByPrimaryKey(new Integer(tncCarItem.getId().toString()));
    }

    @Override
    public Page getCarItemList(QueryObject queryObject) {

        if(queryObject.get("orderField") == null ){
            queryObject.put("orderField","gmt_modified");
            queryObject.put("orderType","desc");
        }
        String ob = queryObject.get("orderField")+" "+queryObject.get("orderType");
        PageHelper.startPage((int)queryObject.get("page"),(int)queryObject.get("limit"),ob);
        List<TncCarItem> list =tncCarItemMapper.getCarItemList(queryObject);
        PageInfo pageInfo = new PageInfo(list);
        return new Page(pageInfo.getTotal(), list);
    }

    @Override
    public int addCarItem(TncCarItem tncCarItem) {
        tncCarItem.setStatus((byte) 0);
        tncCarItem.setIsDeleted((byte) 0);
        tncCarItem.setGmtCreate(new Date());
        tncCarItem.setGmtModified(new Date());
        return tncCarItemMapper.insertSelective(tncCarItem);
    }

    @Override
    public TncCarItem checkRepetive(String number) {
        return tncCarItemMapper.checkRepetive(number);
    }

    @Override
    public int updateCarItem(TncCarItem tncCarItem) {
        tncCarItem.setGmtModified(new Date());
        return tncCarItemMapper.updateByPrimaryKeySelective(tncCarItem);
    }

    @Override
    public int updateCarItemStatus(Integer id, Byte status) {
//        TncCarItem tncCarItem = new TncCarItem();
//        tncCarItem.setNumber(null);
//        tncCarItem.setCarId(Long.valueOf(id));
//        tncCarItem.setStatus(status);
//        tncCarItem.setGmtModified(null);
//        tncCarItem.setGmtCreate(null);

        //return tncCarItemMapper.updateByPrimaryKeySelective(tncCarItem);

        return tncCarItemMapper.updateCarItemStatus(id,status);
    }

    @Override
    public int updateCarItemStatusSubQ(Integer id, Byte status) {

        return  tncCarItemMapper.updateCarItemStatus(id,status);
    }

    @Override
    public Page findCarItemListBySearch(QueryObject queryObject) {
        PageHelper.startPage((int)queryObject.get("page"),(int)queryObject.get("limit"),true);
        List<TncCarItem> list =tncCarItemMapper.getCarItemList(queryObject);
        PageInfo pageInfo = new PageInfo(list);

        return new Page(pageInfo.getTotal(), list);
    }

    @Transactional
    @Override
    public int batchShelves(String dataList) {
        JSONArray json = JSONArray.parseArray(dataList);
        for(int i=0;i<json.size();i++){
            JSONObject job = json.getJSONObject(i);
            TncCarItem tncCarItem =JSONObject.toJavaObject(job,TncCarItem.class);
            tncCarItem.setNumber(null);
            tncCarItem.setStatus((byte) 0);
            tncCarItem.setIsDeleted((byte) 0);
            tncCarItem.setGmtModified(new Date());
            tncCarItemMapper.updateByPrimaryKeySelective(tncCarItem);
        }
        return 0;
    }
    @Transactional
    @Override
    public int repairCarItemList(String dataList) {
        JSONArray json = JSONArray.parseArray(dataList);
        for(int i=0;i<json.size();i++){
            JSONObject job = json.getJSONObject(i);
            TncCarItem tncCarItem =JSONObject.toJavaObject(job,TncCarItem.class);
            tncCarItem.setGmtModified(new Date());
            tncCarItemMapper.updateCarItemStatus(new Integer(job.get("id").toString()) ,new Byte(job.get("status").toString()));
        }
        return 0;
    }

    @Override
    public int quantityAndResidualPlusOne(Long carId) {
        return tncCarItemMapper.quantityAndResidualPlusOne(carId);
    }

    @Override
    public int quantitySubOne(Integer id) {
        return tncCarItemMapper.quantitySubOne(id);
    }

    @Override
    public int residualAddOne(Integer id) {
        return tncCarItemMapper.residualAddOne(id);
    }

    @Override
    public int residualSubOne(Integer id) {
        return tncCarItemMapper.residualSubOne(id);
    }

    @Override
    public int quantityAndResidualSubOne(Integer id) {
        return tncCarItemMapper.quantityAndResidualSubOne(id);
    }
}
