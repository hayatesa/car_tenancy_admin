package com.dev.main.tenancy.service.impl;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.dao.TncBrandMapper;
import com.dev.main.tenancy.dao.TncCouponPointMapper;
import com.dev.main.tenancy.domain.TncBrand;
import com.dev.main.tenancy.domain.TncCouponPoint;
import com.dev.main.tenancy.domain.TncCustomer;
import com.dev.main.tenancy.service.ICouponPointService;
import com.dev.main.tenancy.vo.TncCustomerVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class CouponPointServiceImpl implements ICouponPointService {


    @Autowired
    private TncCouponPointMapper tncCouponPointMapper;

    @Override
    public void addCouponPoint(TncCouponPoint tncCouponPoint) {
        tncCouponPoint.setGmtModified(new Date());
        if(tncCouponPoint.getId()==null) {
            tncCouponPoint.setStatus((byte)1);
            tncCouponPoint.setGmtCreate(new Date());
            tncCouponPointMapper.insertSelective(tncCouponPoint);
        } else {
            tncCouponPointMapper.updateByPrimaryKeySelective(tncCouponPoint);
        }
    }

    @Override
    public TncCouponPoint findByPrimaryKey(Long id) {
        return tncCouponPointMapper.selectByPrimaryKey(id);
    }

    @Override
    public void modifiedByPrimaryKeySelective(TncBrand record) {
    }

    @Override
    public Page queryByPage(QueryObject queryObject) {
        PageHelper.startPage((int) queryObject.get("page"), (int)queryObject.get("limit"), true);
        List<TncCouponPoint> list = tncCouponPointMapper.query(queryObject);
        PageInfo pageInfo = new PageInfo(list);
        return new Page(pageInfo.getTotal(), list);
    }

    @Override
    public void disable(Long id, int select) {
        /**0、禁用 1、解禁  3、删除*/
        TncCouponPoint tncCouponPoint = new TncCouponPoint();
        tncCouponPoint.setId(id);
        tncCouponPoint.setGmtModified(new Date());
        if(select==1) {
            tncCouponPoint.setStatus((byte)1);
        }else if(select==0) {
            tncCouponPoint.setStatus((byte)0);
        }
        tncCouponPointMapper.updateByPrimaryKeySelective(tncCouponPoint);
    }
}
