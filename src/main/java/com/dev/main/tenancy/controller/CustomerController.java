package com.dev.main.tenancy.controller;

import com.alibaba.fastjson.JSONObject;
import com.dev.main.common.statics.StatusCode;
import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.shiro.controller.exception.ShiroExceptionResolver;
import com.dev.main.tenancy.domain.TncAddress;
import com.dev.main.tenancy.domain.TncCustomer;
import com.dev.main.tenancy.service.ICustomerService;
import com.dev.main.tenancy.service.IRegionService;
import com.dev.main.tenancy.vo.TncCustomerVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@ShiroExceptionResolver
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IRegionService regionService;
    /**列表*/
    @GetMapping("/list")
    public Page list(@RequestParam(required = false) Integer page,
                     @RequestParam(required = false) Integer limit,
                     @RequestParam(required = false) String search,
                     @RequestParam(required = false) String orderField,
                     @RequestParam(required = false) String orderType) {
        QueryObject queryObject = new QueryObject(page, limit, search, orderField, orderType);
        return customerService.queryByPage(queryObject);
    }
    /**禁不禁用*/
    @PostMapping("/disable")
    public ResultMap disable(String uid, String select) {
        int select_disable = Integer.valueOf(select);
        customerService.disable_delete(Long.valueOf(uid), select_disable);
        return ResultMap.success();
    }
    /**删除*/
    @PostMapping("/delete")
    public ResultMap delete(String uid,String select) {
        int select_disable = Integer.valueOf(select);
        customerService.disable_delete(Long.valueOf(uid), select_disable);
        return ResultMap.success();
    }

    /**添加*/
    @PostMapping("/save")
    public ResultMap save(@RequestBody TncCustomer tncCustomer) {
        tncCustomer.setStatus((byte)1);
        tncCustomer.setIsDeleted((byte)0);
        tncCustomer.setGmtCreate(new Date());
        tncCustomer.setGmtModified(new Date());
        return ResultMap.success();
    }

    @GetMapping("/edit")
    public ResultMap edit(String uid) {
        ResultMap result = new ResultMap();
        TncCustomerVo tncCustomerVo = customerService.findCustomerVo(Long.valueOf(uid));
        result.put("data", tncCustomerVo);
        return result;
    }

    @GetMapping("/address")
    public ResultMap address(String aid, byte level) {
        ResultMap result = new ResultMap();
        Long id =  Long.valueOf(aid);
        result.put("data", regionService.findAddress(id, level));
        return  result;
    }

    @PostMapping("/change")
    public ResultMap change(@RequestBody TncCustomerVo tncCustomerVo) {
        customerService.changeInfo(tncCustomerVo);
        return ResultMap.success();
    }

    public void setRegionService(IRegionService regionService) {
        this.customerService = customerService;
    }
}
