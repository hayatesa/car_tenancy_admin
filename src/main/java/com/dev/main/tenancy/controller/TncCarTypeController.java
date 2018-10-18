package com.dev.main.tenancy.controller;

import com.dev.main.common.controller.exception.GlobalExceptionResolver;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.domain.TncCarType;
import com.dev.main.tenancy.service.ITncCarTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/carType")
@GlobalExceptionResolver
public class TncCarTypeController {

    @Autowired
    private ITncCarTypeService tncCarTypeService;

    @GetMapping("/list")
    public ResultMap getCarTypeList(){
        List<TncCarType> list = tncCarTypeService.getCarTypeList();
        ResultMap map = new ResultMap();
        map.put("data",list);
        return map;
    }
}
