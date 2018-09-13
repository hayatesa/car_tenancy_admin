package com.dev.main.tenancy.controller;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.shiro.controller.exception.ShiroExceptionResolver;
import com.dev.main.tenancy.service.IRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ShiroExceptionResolver
@RequestMapping("/api/region")
public class RegionController {

    @Autowired
    private IRegionService regionService;

    @GetMapping("/list")
    public Page list(Integer page, Integer limit) {
        QueryObject queryObject = new QueryObject(page, limit);
        return regionService.queryByPage(queryObject);
    }

    public void setRegionService(IRegionService regionService) {
        this.regionService = regionService;
    }
}
