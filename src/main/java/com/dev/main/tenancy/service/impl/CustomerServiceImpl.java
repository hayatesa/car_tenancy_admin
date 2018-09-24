package com.dev.main.tenancy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dev.main.common.util.*;
import com.dev.main.tenancy.dao.TncCustomerMapper;
import com.dev.main.tenancy.domain.AddressRegion;
import com.dev.main.tenancy.domain.TncAddress;
import com.dev.main.tenancy.domain.TncCustomer;
import com.dev.main.tenancy.service.ICustomerService;
import com.dev.main.tenancy.vo.TncCustomerVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
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
        return resultMap;
    }

    @Override
    public TncCustomerVo findCustomerVo(Long uid) {
        TncCustomerVo tncCustomerVo = tncCustomerMapper.findVo(uid);
        return tncCustomerVo;
    }

    @Override
    public ResultMap changeInfo(JSONObject jpsCustomer) {
        ResultMap resultMap = new ResultMap();
        Object tncAddress = jpsCustomer.get("tncAddress");

        TncCustomer tncCustomer = new TncCustomer();
        tncCustomer.setId(jpsCustomer.getLong("id"));
        tncCustomer.setName((jpsCustomer.getString("name")));
        tncCustomer.setGender(jpsCustomer.getByte("gender"));
        tncCustomer.setIdCard(jpsCustomer.getString("idCard"));
        tncCustomer.setPhone((jpsCustomer.getString("phone")));
        tncCustomer.setEmail(jpsCustomer.getString("email"));
        tncCustomer.setEmergencyName(jpsCustomer.getString("emergencyName"));
        tncCustomer.setEmergencyPhone(jpsCustomer.getString("emergencyPhone"));
        tncCustomer.setGmtModified(new Date());
        String password = jpsCustomer.getString("password");
        if(StringUtils.isEmpty(password)) {
            // 产随机产生6位数作为盐值
            String salt = RandomUtil.getRandomNumString(6);
            // 盐值加密
            password = CryptographyUtil.MD5Hash(password, salt);
            tncCustomer.setSalt(salt);
            tncCustomer.setPassword(password);
        }
        int res = tncCustomerMapper.updateByPrimaryKeySelective(tncCustomer);
        if (res > 0) {
            resultMap.put("msg", "编辑成功");
        } else {
            resultMap.put("msg", "编辑失败");
        }
        return null;
    }

    public void setTncCustomerMapper(TncCustomerMapper tncCustomerMapper) {
        this.tncCustomerMapper = tncCustomerMapper;
    }
}
