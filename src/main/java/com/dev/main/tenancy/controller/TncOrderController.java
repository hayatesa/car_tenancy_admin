package com.dev.main.tenancy.controller;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.domain.TncOrder;
import com.dev.main.tenancy.domain.TncCustomer;
import com.dev.main.tenancy.service.IOrderService;
import com.dev.main.tenancy.vo.TncCustomerVo;
import com.dev.main.tenancy.vo.TncOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/order")
public class TncOrderController {

    @Autowired
    IOrderService orderService;
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Page listOrder(@RequestParam(required = false) Integer page,
                        @RequestParam(required = false) Integer limit,
                        @RequestParam(required = false) String search,
                        @RequestParam(required = false) String orderField,
                        @RequestParam(required = false) String orderType) {
        QueryObject queryObject = new QueryObject(page, limit, search, orderField, orderType);
        return orderService.queryByPage(queryObject);
    }
    @RequestMapping(value = "/selectByWord", method = RequestMethod.GET)
    public Page selectByWord(@RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer limit,
                          @RequestParam(required = false) String search,
                          @RequestParam(required = false) String orderField,
                          @RequestParam(required = false) String orderType,
                             @RequestParam Byte status,
                             @RequestParam(required = false) Long gp,
                             @RequestParam(required = false) Long gc,
                             @RequestParam(required = false) Long ga,
                             @RequestParam(required = false) Long bp,
                             @RequestParam(required = false) Long bc,
                             @RequestParam(required = false) Long ba){
                             //@RequestParam(required = false) String now) {
        QueryObject queryObject = new QueryObject(page, limit, search, orderField, orderType);
        queryObject.put("status",status);
        queryObject.put("gp",gp);queryObject.put("gc",gc);queryObject.put("ga",ga);
        queryObject.put("bp",bp);queryObject.put("bc",bc);queryObject.put("ba",ba);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            Date time = formatter.parse(now);
//            queryObject.put("now",time);
//            System.out.println(time);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return orderService.queryByPage_Word(queryObject);
    }


    @PostMapping("/updateStatus")
    public ResultMap update(@RequestBody TncOrder tncOrder){
       // System.out.println("updateStatus");
       // System.out.println(tncOrder);
        int result = orderService.update(tncOrder);
        if(result < 0){
            return ResultMap.fail(-1,"状态修改失败");
        }
        return ResultMap.success("状态修改成功");
    }
    @PostMapping("/delete")
    public ResultMap delete(Long id){
        // System.out.println("updateStatus");
        // System.out.println(tncOrder);
        TncOrder tncOrder = new TncOrder();
        tncOrder.setId(id);
        tncOrder.setIsDeleted((byte) 1);
        tncOrder.setGmtModified(new Date());
        int result = orderService.modifiedByPrimaryKeySelective(tncOrder);
        if(result < 0){
            return ResultMap.fail(-1,"删除失败");
        }
        return ResultMap.success("删除成功");
    }

//    @PostMapping("/updateUser")
//    public ResultMap updateUser(RequestBody TncCustomer tn){
//        int result = orderService.updateUser();
//        if(result < 0){
//            return ResultMap.fail(-1,"状态修改失败");
//        }
//        return ResultMap.success("状态修改成功");
//    }

    @GetMapping("/selectById")
    public ResultMap selectById(Long id){
        TncOrderVo tncOrderVo = orderService.findByPrimaryKey(id);
        ResultMap resultMap = new ResultMap();
        resultMap.put("data", tncOrderVo);
        return resultMap;
    }
    @GetMapping("/selectUser")
    public ResultMap selectUser(String phone){
        TncCustomerVo tncCustomer = orderService.findUser(phone);
        ResultMap resultMap = new ResultMap();
        resultMap.put("data", tncCustomer);
        return resultMap;
    }
}
