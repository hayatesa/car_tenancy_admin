package com.dev.main.tenancy.service.impl;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.dao.*;
import com.dev.main.tenancy.domain.*;
import com.dev.main.tenancy.service.IOrderService;
import com.dev.main.tenancy.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public ResultMap findByPrimaryKey(Long id) {
        TncOrderPriceVo priceVo = new TncOrderPriceVo();
        TncOrderVo tncOrderVo = new TncOrderVo();
        TncOrder order = tncOrderMapper.selectByPrimaryKey(id);
        //获取时间信息
        Date getdate = order.getStartDate();
        Date returndate = order.getReturnDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String gd = DateFormat.getDateInstance().format(getdate);
        String rd = DateFormat.getDateInstance().format(returndate);
        String gd1 = dateFormat.format(getdate);
        String rd1 = dateFormat.format(returndate);
        int days = (int) ((returndate.getTime() - getdate.getTime()) / (1000*3600*24));
        Long plus = (returndate.getTime() - getdate.getTime()) % (1000*3600*24);
        int hour = 0;
        if(plus>(1000*3600*4)) days++;
        else if(plus!=0){
            hour = (int) (plus / (1000*3600));
            if(hour==0||(plus%(1000*3600))>0) hour++;
        }
        priceVo.setOvertime_count(hour);
        priceVo.setDays(days);
        priceVo.setGetDate(gd);
        priceVo.setGetTime(gd1);
        priceVo.setReturnDate(rd);
        priceVo.setReturnTime(rd1);
        //获取价格信息
        BigDecimal baseAmount = order.getBaseAmount();
        BigDecimal serviceAmount = order.getServiceAmount();
        BigDecimal discount = order.getDiscount();
        priceVo.setTotal_base_price(baseAmount); //基础总价
        priceVo.setTotal_service_price(serviceAmount);  //服务总价
        priceVo.setDiscount_total_base(discount.multiply(baseAmount));//折扣基础总价
        priceVo.setDiscount_total_service(discount.multiply(serviceAmount));//折扣基础服务费总价
        int base = (baseAmount.divideToIntegralValue(new BigDecimal(days))).intValue();
        int service = (serviceAmount.divideToIntegralValue(new BigDecimal(days))).intValue();
//        System.out.println("days"+days);
//        System.out.println(baseAmount.divideToIntegralValue(new BigDecimal(days)));
        priceVo.setBase_price(base);
        priceVo.setService_price(service);
        //获取优惠券
        TncCoupon tncCoupon = tncCouponMapper.selectByPrimaryKey(order.getCouponId());
        if(tncCoupon==null) tncOrderVo.setAmount("无");
        else tncOrderVo.setAmount("-"+tncCouponMapper.selectByPrimaryKey(order.getCouponId()).getAmount().toString());
        //System.out.println(tncCouponMapper.selectByPrimaryKey(order.getCouponId()).getAmount());
        //获取用户信息
        TncCustomer tc = tncCustomerMapper.selectByPhone(order.getPhone());
        tncOrderVo.setTncOrder(order);
        if(tc.getName().equals(order.getName())){
            tncOrderVo.setTncCustomer(tc);
        }
        else {
            TncCustomer tcc = new TncCustomer();
            tcc.setName(order.getName());
            tcc.setEmail(order.getEmail());
            tcc.setPhone(order.getPhone());
            tcc.setIdCard(order.getCredentialsNumber());
            tcc.setEmergencyName(tc.getName());
            tcc.setEmergencyPhone(order.getPhone());
            tncOrderVo.setTncCustomer(tcc);
        }

        //获取车辆信息
        CarVo carVo = tncOrderMapper.getCar(order.getCarItemId());
        tncOrderVo.setCarNub(carVo.getNub());
        tncOrderVo.setPicPath(carVo.getPath());
        tncOrderVo.setCarSeries(carVo.getSeries());
        tncOrderVo.setCarName(carVo.getBrand());
        tncOrderVo.setCarId(carVo.getCarId());
        //System.out.println(priceVo.toString());
        ResultMap resultMap = new ResultMap();
        resultMap.put("data", tncOrderVo);
        resultMap.put("price",priceVo);
        return resultMap;
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
            if (tncOrder.getOtherAmount() != null&& new BigDecimal(0).compareTo(tncOrder.getOtherAmount())!=0) {//有其他费用
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
                tncOrder.setRealReturnDate(new Date());
                int rs1 = tncOrderMapper.updateByPrimaryKeySelective(tncOrder);
                TncOrder tt = tncOrderMapper.selectByPrimaryKey(tncOrder.getId());
                //car可用数加一
                Long carid = tncOrderMapper.getCarId(tt.getCarItemId());
                System.out.println("carid"+carid);
                int rs3 = tncOrderMapper.updateCarNubUp(carid);
                //将caritem标记为可用
                int rs2 = tncCarItemMapper.updateCarItemStatus(Integer.parseInt(tt.getCarItemId().toString()), (byte) 0);
                if (rs1 > 0 && rs2 > 0 && rs3 > 0) {
                    return 1;
                } else {
                    return 0;
                }
        } else if (status == 1) {//确认提车
            tncOrder.setGmtModified(new Date());
            return tncOrderMapper.updateByPrimaryKeySelective(tncOrder);
        } else if (status == 3) {//取消提车、确认退款
            int rs1 = tncOrderMapper.updateByPrimaryKeySelective(tncOrder);
            System.out.println("前台传入的orderid"+tncOrder.getId());
            TncOrder tt = tncOrderMapper.selectByPrimaryKey(tncOrder.getId());
            System.out.println(tt.toString());
            System.out.println("catitemid-----------"+tt.getCarItemId());
            int rs2 = tncCarItemMapper.updateCarItemStatus(Integer.parseInt(tt.getCarItemId().toString()), (byte) 0);
            Long carid = tncOrderMapper.getCarId(tt.getCarItemId());
            int rs3 = tncOrderMapper.updateCarNubUp(carid);
            if (rs1 > 0 && rs2 > 0 && rs3 > 0 ) {
                return 1;
            } else {
                return 0;
            }
        }
        else return 0;
    }

    @Override
    public TncCustomerVo findUser(Long id) {
        System.out.println(id);
        TncOrder order = tncOrderMapper.selectByPrimaryKey(id);
        System.out.println(order.toString());
        String phone = order.getPhone();
        TncCustomerVo tncCustomerVo = tncCustomerMapper.findVo(tncCustomerMapper.selectByPhone(phone).getId());
        if(tncCustomerVo.getName().equals(order.getName())) return tncCustomerVo;
        else {
            TncCustomerVo tcc = new TncCustomerVo();
            tcc.setName(order.getName());
            tcc.setEmail(order.getEmail());
            tcc.setPhone(order.getPhone());
            tcc.setIdCard(order.getCredentialsNumber());
            tcc.setEmergencyName(tncCustomerVo.getName());
            tcc.setEmergencyPhone(order.getPhone());
            return tcc;
        }
    }

    @Override
    public int delete(Long id) {
        return tncOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ResultMap selectCarId(Long carId) {
        List<String> list = tncOrderMapper.getCarNub(carId);
        return new ResultMap().success("获取成功").put("carNub",list);
    }

    @Override
    public int updateCarNub(String carNubBefore, String carNubNew) {

        int rs1 = tncOrderMapper.updateCarNub(carNubBefore,(byte)0);
        int rs2 = tncOrderMapper.updateCarNub(carNubNew,(byte)1);
        if(rs1>0 && rs2>0) return 1;
        else return 0;
    }

    @Override
    public int updateCarItemId(Long orderId, String carNubBefore, String carNubNew) {
        Long newItemId = tncOrderMapper.getCarItemIdByNub(carNubNew);
        int result = tncOrderMapper.updateCarItemId(orderId,newItemId);
        return result;
    }

    @Override
    public int updateUser(TncCustomer customer) {
        int result = tncCustomerMapper.updateByPrimaryKeySelective(customer);
        return result;
    }
}
