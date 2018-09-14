package com.dev.main.tenancy.controller;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.shiro.controller.exception.ShiroExceptionResolver;
import com.dev.main.tenancy.service.IRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@ShiroExceptionResolver
@RequestMapping("/api/region")
public class RegionController {

    @Autowired
    private IRegionService regionService;

    @GetMapping("/list")
    public Page list(@RequestParam(required = false) Integer page,
                     @RequestParam(required = false) Integer limit,
                     @RequestParam(required = false) String search,
                     @RequestParam(required = false) String orderField,
                     @RequestParam(required = false) String orderType) {
        QueryObject queryObject = new QueryObject(page, limit, search, orderField, orderType);
        return regionService.queryByPage(queryObject);
    }

    public void setRegionService(IRegionService regionService) {
        this.regionService = regionService;
    }
}
