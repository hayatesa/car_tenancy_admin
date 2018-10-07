package com.dev.main.tenancy.service.impl;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.dao.*;
import com.dev.main.tenancy.domain.*;
import com.dev.main.tenancy.service.IOrderService;
import com.dev.main.tenancy.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static sun.security.krb5.Confounder.intValue;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private TncOrderMapper tncOrderMapper;
    @Autowired
    private TncCarMapper tncCarMapper;
    @Autowired
    private TncBrandMapper tncBrandMapper;
    @Autowired
    private TncCarItemMapper tncCarItemMapper;
    @Autowired
    private TncCarPicMapper tncCarPicMapper ;
    @Autowired
    private TncCustomerMapper tncCustomerMapper;
    @Autowired
    private TncCouponMapper tncCouponMapper;

    @Override
    public Page queryByPage(QueryObject queryObject) {
        PageHelper.startPage((int) queryObject.get("page"), (int)queryObject.get("limit"), true);
        List<TncOrderListVo> list = tncOrderMapper.query2(queryObject);
//        StoreVo store = tncOrderMapper.getStore(new Long(1));
//        System.out.println(store);
//        CarVo car = tncOrderMapper.getCar(new Long(1));
//        System.out.println(car);
        //List<OrderListVo> list = tncOrderMapper.getOrderList(queryObject);
//        for (TncOrderListVo vo: list) {
//            System.out.println(vo);
//        }
        PageInfo pageInfo = new PageInfo(list);
        return new Page(pageInfo.getTotal(), list);
    }

    @Override
    public Page queryByPage_Word(QueryObject queryObject) {
        PageHelper.startPage((int) queryObject.get("page"), (int)queryObject.get("limit"), true);
        System.out.println(queryObject.toString());
        List<TncOrderListVo> list = tncOrderMapper.queryByWord(queryObject);
        PageInfo pageInfo = new PageInfo(list);
        return new Page(pageInfo.getTotal(), list);
    }

    @Override
    public TncOrderVo findByPrimaryKey(Long id) {
        TncOrderVo vo = new TncOrderVo();
        TncOrder to = tncOrderMapper.selectByPrimaryKey(id);
        TncCoupon tncCoupon = tncCouponMapper.selectByPrimaryKey(to.getCouponId());
        if(tncCoupon==null) vo.setAmount("无");
        else vo.setAmount("-"+tncCouponMapper.selectByPrimaryKey(to.getCouponId()).getAmount().toString());
        //System.out.println(tncCouponMapper.selectByPrimaryKey(to.getCouponId()).getAmount());

        TncCustomer tc = tncCustomerMapper.selectByPhone(to.getPhone());
        vo.setTncOrder(to);vo.setTncCustomer(tc);

        CarVo carVo = tncOrderMapper.getCar(to.getCarItemId());
        vo.setCarNub(carVo.getNub());
        vo.setPicPath(carVo.getPath());
        vo.setCarSeries(carVo.getSeries());
        vo.setCarName(carVo.getBrand());
        return vo;
    }

    @Override
    public int modifiedByPrimaryKeySelective(TncOrder tncOrder) {
        return tncOrderMapper.updateByPrimaryKeySelective(tncOrder);
    }

    @Override
    public int update(TncOrder tncOrder) {
        int status = tncOrder.getStatus();
        System.out.println("-----------"+tncOrder.toString());
        if (status == 4) {//确认还车
            if (tncOrder.getOtherAmount() != null) {//有其他费用
                String des = tncOrder.getDescription();
                BigDecimal otham = tncOrder.getOtherAmount();
                TncOrder to = tncOrderMapper.selectByPrimaryKey(tncOrder.getId());
                BigDecimal sum = to.getTotalAmount();//原订单总价
                String a = to.getDescription();//原其他费用详情
                BigDecimal b = to.getOtherAmount();//原其他费用总数
                tncOrder.setDescription(a + des);
                tncOrder.setOtherAmount(b.add(otham));
                tncOrder.setTotalAmount(sum.add(otham));
            };
                int rs1 = tncOrderMapper.updateByPrimaryKeySelective(tncOrder);
                TncOrder tt = tncOrderMapper.selectByPrimaryKey(tncOrder.getId());
                int rs2 = tncCarItemMapper.updateCarItemStatus(Integer.parseInt(tt.getCarItemId().toString()), (byte) 0);
                if (rs1 > 0 && rs2 > 0) {
                    return 1;
                } else {
                    return 0;
                }
        } else if (status == 1) {//确认提车
            return tncOrderMapper.updateByPrimaryKeySelective(tncOrder);
        } else if (status == 3) {//取消提车、确认退款
            int rs1 = tncOrderMapper.updateByPrimaryKeySelective(tncOrder);
            int rs2 = tncCarItemMapper.updateCarItemStatus(Integer.parseInt(tncOrder.getCarItemId().toString()), (byte) 0);
            if (rs1 > 0 && rs2 > 0) {
                return 1;
            } else {
                return 0;
            }
        }
        else return 0;
    }

    @Override
    public TncCustomerVo findUser(String phone) {
        return tncCustomerMapper.findVo(tncCustomerMapper.selectByPhone(phone).getId());
    }

    @Override
    public int delete(Long id) {
        return tncOrderMapper.deleteByPrimaryKey(id);
    }
}
