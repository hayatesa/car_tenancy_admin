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

    @GetMapping("/get")
    public ResultMap getCarByCarId(Long carId){
        ResultMap map = new ResultMap();
        TncCar car = carService.getCarByCarId(carId);
        map.put("data",car);
        return map;
    }
    @GetMapping("/delete")
    public ResultMap deleteCarByCarId(Long carId){
        int n = carService.deleteCarByCarId(carId);
        if (n>0){
            return ResultMap.success();
        }else {
            return ResultMap.fail("添加失败");
        }
    }
}
