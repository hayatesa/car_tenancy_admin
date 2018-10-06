package com.dev.main.tenancy.controller;

import com.dev.main.common.controller.exception.GlobalExceptionResolver;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.domain.TncPriceScheme;
import com.dev.main.tenancy.service.ITncPriceSchemeService;
import com.dev.main.tenancy.vo.TncCarPriceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/priceScheme")
@GlobalExceptionResolver
public class TncPriceSchemeController {

    @Autowired
    private ITncPriceSchemeService tncPriceSchemeService;

    /**
     * @param  carID
     * @param packageID
     * @param deposit
     * @param discount
     * @param basePrice
     * @param servicePrice
     * */
    @GetMapping("/add")
    public ResultMap addScheme(TncPriceScheme tncPriceScheme){
        int  n = tncPriceSchemeService.addPriceScheme(tncPriceScheme);
        return ResultMap.success();
    }


    @GetMapping("/list")
    public ResultMap listPrice( Integer carId){

        ResultMap map = new ResultMap();
        System.out.println(carId);
        List<TncCarPriceVo> list = tncPriceSchemeService.selectCarPriceList(carId);

        map.put("data",list);
        map.put("count",list.size());
        return map;

    }

    @GetMapping("/update")
    public ResultMap updatePrice(TncPriceScheme tncPriceScheme){

       int n = tncPriceSchemeService.updatePrice(tncPriceScheme);
       if (n==1){
           return ResultMap.success();
       }else {
           return ResultMap.fail("更新失败");
       }
    }

    @GetMapping("/delete")
    public ResultMap deletePrice(Long id){
        System.out.println(id);

        int n = tncPriceSchemeService.deletePrice(id);

        if (n==1){
            return ResultMap.success();
        }else {
            return ResultMap.fail("删除失败");
        }
    }

    @GetMapping(value = "/batchDelete")
    public ResultMap batchShelves(String dataList){
        int  n = tncPriceSchemeService.batchDelete(dataList);
        return ResultMap.success();
    }


}
