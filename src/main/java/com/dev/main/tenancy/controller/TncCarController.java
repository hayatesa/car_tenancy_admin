package com.dev.main.tenancy.controller;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.domain.TncCar;
import com.dev.main.tenancy.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        System.out.println("search--"+search);
        System.out.println("orderField--++"+orderField);
        System.out.println("orderType--++"+orderType);
        return carService.getCarList(queryObject);
    }


    @GetMapping("/add")
    public ResultMap addCar(TncCar tncCar){
        int n = carService.addCar(tncCar);

        if (n>0){
            return ResultMap.success();
        }else {
            return ResultMap.fail("添加失败");
        }
    }
}
