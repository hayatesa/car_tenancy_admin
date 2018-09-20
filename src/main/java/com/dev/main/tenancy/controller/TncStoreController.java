package com.dev.main.tenancy.controller;

import com.alibaba.fastjson.JSONObject;
import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.shiro.controller.exception.ShiroExceptionResolver;
import com.dev.main.tenancy.domain.TncStore;
import com.dev.main.tenancy.service.ITncStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@ShiroExceptionResolver
@RequestMapping("/api/tncStore")
public class TncStoreController {

    @Autowired
    private ITncStoreService iTncStoreService;

    public void setiTncStoreService(ITncStoreService iTncStoreService) {
        this.iTncStoreService = iTncStoreService;
    }


    /**
     * 门店列表
     * @param page
     * @param limit
     * @param search
     * @param orderField
     * @param orderType
     * @return
     */
    @RequestMapping("/list")
    public Page StoreList(@RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer limit,
                          @RequestParam(required = false) String search,
                          @RequestParam(required = false) String orderField,
                          @RequestParam(required = false) String orderType) {
        QueryObject queryObject = new QueryObject(page,limit,search,orderField,orderType);
        return iTncStoreService.queryByPage(queryObject);
    }

    @RequestMapping("/add")
    public ResultMap StoreAdd(@RequestBody String data){
        JSONObject jsonObject = JSONObject.parseObject(data);
        TncStore tncStore = new TncStore();
        tncStore.setIsDeleted((byte) 0);
        Date d = new Date();
        tncStore.setGmtCreate(d);
        tncStore.setGmtModified(d);
        ResultMap resultMap = new ResultMap();
        return resultMap;
    }

}
