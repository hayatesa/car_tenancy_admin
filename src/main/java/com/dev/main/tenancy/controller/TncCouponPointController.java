package com.dev.main.tenancy.controller;

import com.alibaba.fastjson.JSONObject;
import com.dev.main.common.statics.StatusCode;
import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.shiro.controller.exception.ShiroExceptionResolver;
import com.dev.main.tenancy.domain.TncBrand;
import com.dev.main.tenancy.domain.TncCouponPoint;
import com.dev.main.tenancy.service.ICouponPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@ShiroExceptionResolver
@RequestMapping("/api/tncCouponPoint")
public class TncCouponPointController {
    @Autowired
    private ICouponPointService couponPointService;

    @GetMapping("/list")
    public Page BrandList(@RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer limit,
                          @RequestParam(required = false) String search,
                          @RequestParam(required = false) String orderField,
                          @RequestParam(required = false) String orderType) {
        QueryObject queryObject = new QueryObject(page, limit, search, orderField, orderType);
        return couponPointService.queryByPage(queryObject);
    }

    /**禁不禁用*/
    @PostMapping("/disable")
    public ResultMap disable(String id, String select) {
        int select_disable = Integer.valueOf(select);
        couponPointService.disable(Long.valueOf(id), select_disable);
        return ResultMap.success();
    }

    @PostMapping("/add")
    public ResultMap couponAdd(@RequestBody TncCouponPoint tncCouponPoint) {
        couponPointService.addCouponPoint(tncCouponPoint);
        return ResultMap.success();
    }

    @PostMapping("/find")
    public ResultMap couponFind(Long cid) {
        ResultMap result = new ResultMap();
        TncCouponPoint tncCouponPoint = couponPointService.findByPrimaryKey(cid);
        result.put("coupon", tncCouponPoint);
        return  result;
    }

    public void setCouponPointService(ICouponPointService couponPointService) {
        this.couponPointService = couponPointService;
    }
}
