package com.dev.main.tenancy.controller;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.domain.TncCarItem;
import com.dev.main.tenancy.service.ICarItemService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/carItem")
public class TncCarItemController {

    @Autowired
    private ICarItemService carItemService;

    /**
     * @Param:carId  车型ID
     * @Return: 车牌列表
     */
    @RequestMapping(value ="/{carId}",method = RequestMethod.GET)
    public Page getCarItemList(
                                @PathVariable("carId") Integer carId,
                                @RequestParam(required = false) Integer page,
                               @RequestParam(required = false) Integer limit,
                               @RequestParam(required = false) String search,
                               @RequestParam(required = false) String orderField,
                               @RequestParam(required = false) String orderType){
        QueryObject queryObject = new QueryObject(page, limit, search, orderField, orderType);
        queryObject.put("carId",carId);
        return carItemService.getCarItemList(queryObject);
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public ResultMap addCarItem(TncCarItem tncCarItem){
            int n = carItemService.addCarItem(tncCarItem);
            return  ResultMap.success();
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResultMap updateCarItem(@RequestBody TncCarItem tncCarItem){
        int n = carItemService.updateCarItem(tncCarItem);
        return  ResultMap.success();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResultMap deleteCarItem(@PathVariable("id") Integer id ){
        int n = carItemService.deleteCarItem(id);
        return  ResultMap.success();
    }

    @RequestMapping(value = "/{id}/{status}",method = RequestMethod.PUT)
    public ResultMap updateCarItem(@PathVariable("id") Integer id,@PathVariable("status") Byte status ){
        int n = carItemService.updateCarItem(id,status);
        return  ResultMap.success();
    }
    @RequestMapping(value = "/search" ,method = RequestMethod.POST)
    public Page findCarItem( @RequestParam(required = false) Integer page,
                                  @RequestParam(required = false) Integer limit,
                                  @RequestParam(required = false) String search,
                                  @RequestParam(required = false) String orderField,
                                  @RequestParam(required = false) String orderType){
        QueryObject queryObject = new QueryObject(page, limit, search, orderField, orderType);

        return carItemService.findCarItemListBySearch(queryObject);
    }

    @RequestMapping(value = "/batchshelves",method = RequestMethod.GET)
    public ResultMap batchShelves(String dataList){
        int  n = carItemService.batchShelves(dataList);
        return ResultMap.success();
    }
    @RequestMapping(value = "/batchrepair",method = RequestMethod.POST)
    public ResultMap batchRepair(String dataList){
        int  n = carItemService.repairCarItemList(dataList);
        return ResultMap.success();
    }

}
