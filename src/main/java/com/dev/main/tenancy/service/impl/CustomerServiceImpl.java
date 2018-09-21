package com.dev.main.tenancy.service.impl;

import com.dev.main.common.util.*;
import com.dev.main.tenancy.dao.TncCustomerMapper;
import com.dev.main.tenancy.domain.AddressRegion;
import com.dev.main.tenancy.domain.TncCustomer;
import com.dev.main.tenancy.service.ICustomerService;
import com.dev.main.tenancy.vo.TncCustomerVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private TncCustomerMapper tncCustomerMapper;

    @Override
    public Page queryByPage(QueryObject queryObject) {
        System.out.println(queryObject);
        PageHelper.startPage((int) queryObject.get("page"), (int)queryObject.get("limit"), true);
        List<TncCustomerVo> list = tncCustomerMapper.queryVo(queryObject);
        PageInfo pageInfo = new PageInfo(list);
        return new Page(pageInfo.getTotal(), list);
    }

    @Override
    public ResultMap disable_delete(Long uid, int select) {
        /**0、禁用 1、解禁  3、删除*/
        TncCustomer tncCustomer = new TncCustomer();
        tncCustomer.setId(uid);
        tncCustomer.setGmtModified(new Date());
        ResultMap resultMap = new ResultMap();

        if(select==1) {
            tncCustomer.setStatus((byte)1);
            int res = tncCustomerMapper.updateByPrimaryKeySelective(tncCustomer);
            if(res > 0){
                resultMap.put("msg","操作成功");
            }else{
                resultMap.put("msg","操作失败");
            }
        }else if(select==0) {
            tncCustomer.setStatus((byte)0);
            int res = tncCustomerMapper.updateByPrimaryKeySelective(tncCustomer);
            if(res > 0){
                resultMap.put("msg","操作成功");
            }else{
                resultMap.put("msg","操作失败");
            }
        }else if(select==3) {
            tncCustomer.setIsDeleted((byte)1);
            int res = tncCustomerMapper.updateByPrimaryKeySelective(tncCustomer);
            if(res > 0){
                resultMap.put("msg","删除成功");
            }else{
                resultMap.put("msg","删除失败");
            }
        }
        return resultMap;
    }

    @Override
    public ResultMap save(TncCustomer tncCustomer) {
        ResultMap resultMap = new ResultMap();

        if(tncCustomer.getId()==null||tncCustomer.getId()==0) {
            if(tncCustomerMapper.selectByPhone(tncCustomer.getPhone())==null) {
                // 产随机产生6位数作为盐值
                String salt = RandomUtil.getRandomNumString(6);
                // 盐值加密
                String password = CryptographyUtil.MD5Hash(tncCustomer.getPassword(), salt);
                tncCustomer.setSalt(salt);
                tncCustomer.setPassword(password);
                int res = tncCustomerMapper.insertSelective(tncCustomer);
                if (res > 0) {
                    resultMap.put("msg", "添加成功");
                } else {
                    resultMap.put("msg", "添加失败");
                }
            } else {
                resultMap.put("repeat","用户已存在");
            }
        }else {
            int res = tncCustomerMapper.updateByPrimaryKeySelective(tncCustomer);
            if(res > 0){
                resultMap.put("msg","编辑成功");
            }else{
                resultMap.put("msg","编辑失败");
            }
        }
        return resultMap;
    }

    public void setTncCustomerMapper(TncCustomerMapper tncCustomerMapper) {
        this.tncCustomerMapper = tncCustomerMapper;
    }
}