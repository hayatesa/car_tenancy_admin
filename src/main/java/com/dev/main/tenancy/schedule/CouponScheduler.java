package com.dev.main.tenancy.schedule;

import com.dev.main.tenancy.dao.TncCouponMapper;
import com.dev.main.tenancy.domain.TncCoupon;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class CouponScheduler {

    @Autowired
    private TncCouponMapper tncCouponMapper;

    /**
     * 每天0点执行优惠券维护任务，把已过期的优惠券设置为过期状态
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void doExpire() {
        int affected = 1; // 受影响行数
        // 执行SQL
        while (affected != 0) { // 执行至没有数据被更新
            System.out.println(affected);
            affected = tncCouponMapper.setExpired();
        }
    }

    public void setTncCouponMapper(TncCouponMapper tncCouponMapper) {
        this.tncCouponMapper = tncCouponMapper;
    }
}
