package com.dev.main.tenancy.controller;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/car")
public class TncCarController {
    @Autowired
    private ICarService carService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Page listCar(@RequestParam(required = false) Integer page,
                        @RequestParam(required = false) Integer limit,
                        @RequestParam(required = false) String search,
                        @RequestParam(required = false) String orderField,
                        @RequestParam(required = false) String orderType){

        QueryObject queryObject = new QueryObject(page, limit, search, orderField, orderType);
        return carService.getCarList(queryObject);
    }

}
