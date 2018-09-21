package com.dev.main.tenancy.controller;

import com.alibaba.fastjson.JSONObject;
import com.dev.main.common.statics.StatusCode;
import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.shiro.controller.exception.ShiroExceptionResolver;
import com.dev.main.tenancy.domain.TncCustomer;
import com.dev.main.tenancy.service.ICustomerService;
import com.dev.main.tenancy.service.IRegionService;
import com.dev.main.tenancy.vo.TncCustomerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@ShiroExceptionResolver
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
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
        ResultMap result = null;
        int select_disable = Integer.valueOf(select);
        result = customerService.disable_delete(Long.valueOf(uid), select_disable);
        result.put("code", StatusCode.SUCCESS);
        return result;
    }
    /**删除*/
    @PostMapping("/delete")
    public ResultMap delete(String uid,String select) {
        ResultMap result = null;
        int select_disable = Integer.valueOf(select);
        result = customerService.disable_delete(Long.valueOf(uid), select_disable);
        result.put("code", StatusCode.SUCCESS);
        return result;
    }

    /**添加兼修改*/
    @PostMapping("/save")
    public ResultMap save(@RequestBody String data) {
        ResultMap result = null;
        JSONObject jpsCustomer = JSONObject.parseObject(data);
        TncCustomer tncCustomer = new TncCustomer();
        tncCustomer.setName(jpsCustomer.getString("name"));
        tncCustomer.setPhone(jpsCustomer.getString("phone"));
        tncCustomer.setPassword(jpsCustomer.getString("password"));
        tncCustomer.setStatus((byte)1);
        tncCustomer.setIsDeleted((byte)0);
        tncCustomer.setGmtCreate(new Date());
        tncCustomer.setGmtModified(new Date());
        result = customerService.save(tncCustomer);
        return result;
    }

    @PostMapping("/edit")
    public ResultMap edit(String uid) {
        ResultMap result = new ResultMap();
        TncCustomerVo tncCustomerVo = customerService.findCustomerVo(Long.valueOf(uid));
        result.put("data", tncCustomerVo);
        return result;
    }

    public void setRegionService(IRegionService regionService) {
        this.customerService = customerService;
    }
}
