package com.dev.main.tenancy.controller;

import com.dev.main.common.controller.exception.GlobalExceptionResolver;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.service.ISchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/scheme")
@GlobalExceptionResolver
public class TncPriceSchemeController {
    @Autowired
    private ISchemeService schemeService;

    @GetMapping("/add")
    public ResultMap addScheme(){


        return ResultMap.success();
    }

}
