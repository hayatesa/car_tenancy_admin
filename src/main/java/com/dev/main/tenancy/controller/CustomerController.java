package com.dev.main.tenancy.controller;

import com.dev.main.common.statics.StatusCode;
import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.shiro.controller.exception.ShiroExceptionResolver;
import com.dev.main.tenancy.service.ICustomerService;
import com.dev.main.tenancy.service.IRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ShiroExceptionResolver
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/list")
    public Page list(@RequestParam(required = false) Integer page,
                     @RequestParam(required = false) Integer limit,
                     @RequestParam(required = false) String search,
                     @RequestParam(required = false) String orderField,
                     @RequestParam(required = false) String orderType) {
        QueryObject queryObject = new QueryObject(page, limit, search, orderField, orderType);
        return customerService.queryByPage(queryObject);
    }

    @PostMapping("/disable")
    public ResultMap disable(String uid, String select) {
        ResultMap result = null;
        int select_disable = Integer.valueOf(select);
        result = customerService.disable_delete(Long.valueOf(uid), select_disable);
        result.put("code", StatusCode.SUCCESS);
        return result;
    }
    @PostMapping("/delete")
    public ResultMap delete(String uid,String select) {
        return null;
    }


    public void setRegionService(IRegionService regionService) {
        this.customerService = customerService;
    }
}
