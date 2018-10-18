package com.dev.main.tenancy.controller;

import com.dev.main.common.controller.exception.GlobalExceptionResolver;
import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.domain.TncCarItem;
import com.dev.main.tenancy.service.ICarItemService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/carItem")
@GlobalExceptionResolver
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
            if(n == 1){
                return  ResultMap.success();
            }else {
                return ResultMap.fail("添加失败");
            }

    }

    @RequestMapping(value = "/checkRepeat",method = RequestMethod.GET)
    public ResultMap checkRepeat(TncCarItem tncCarItem){
        TncCarItem flag =carItemService.checkRepetive(tncCarItem.getNumber());

        if(flag == null){
            return  ResultMap.success();
        }else {
            return  ResultMap.fail("车牌已重复");
        }
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResultMap updateCarItem(@RequestBody TncCarItem tncCarItem){
        int n = carItemService.updateCarItem(tncCarItem);
        if(n==1){
            return  ResultMap.success();
        }else {
            return  ResultMap.fail("修改失败");
        }

    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResultMap deleteCarItem(TncCarItem tncCarItem){
        int n = carItemService.deleteCarItem(tncCarItem);
        if(n==1){
            return  ResultMap.success();
        }else {
            return  ResultMap.fail("删除失败");
        }

    }

    @RequestMapping(value = "/deleteSubQ",method = RequestMethod.GET)
    public ResultMap deleteCarItemSubQ(TncCarItem tncCarItem){
        int n = carItemService.deleteCarItemSubQ(tncCarItem);
        if(n==1){
            return  ResultMap.success();
        }else {
            return  ResultMap.fail("删除失败");
        }

    }


    @RequestMapping(value = "/{id}/{status}",method = RequestMethod.PUT)
    public ResultMap updateCarItem(@PathVariable("id") Integer id,@PathVariable("status") Byte status ){
        int n = carItemService.updateCarItemStatus(id,status);
        return  ResultMap.success();
    }

    @RequestMapping(value = "scrapSubQ/{id}/{status}",method = RequestMethod.PUT)
    public ResultMap updateCarItemSubQ(@PathVariable("id") Integer id,@PathVariable("status") Byte status ){
        int n = carItemService.updateCarItemStatusSubQ(id,status);
        if(n==1){
            return  ResultMap.success();
        }else {
            return  ResultMap.fail("报废失败");
        }
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
