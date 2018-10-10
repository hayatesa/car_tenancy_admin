package com.dev.main.tenancy.controller;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.domain.TncAds;
import com.dev.main.tenancy.service.IAdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  * Description: catr_tenancy_admin
 *  * Created by sf on 2018/9/14 9:01
 *  
 */
@RestController
@RequestMapping("/api/Ads")
public class AdsController {
    @Autowired
    public IAdsService adsService;

    @GetMapping("/all")
    public Page getPage(@RequestParam(required = false) Integer page,
                        @RequestParam(required = false) Integer limit,
                        @RequestParam(required = false) String search,
                        @RequestParam(required = false) String orderField,
                        @RequestParam(required = false) String orderType){
        QueryObject queryObject = new QueryObject(page, limit, search, orderField, orderType);
        return adsService.queryByPage(queryObject);
    }
    @GetMapping("/save")
    public ResultMap addAds(TncAds tncAds){
        ResultMap resultMap = adsService.addAds(tncAds);
        return resultMap;
    }
    @PostMapping("/save")
    public ResultMap updateAds(TncAds tncAds){
        ResultMap resultMap = adsService.updateAds(tncAds);
        return resultMap;
    }
    @GetMapping("/del")
    public ResultMap deleteAds(TncAds tncAds){
        ResultMap resultMap = adsService.deleteAds(tncAds);
        return resultMap;
    }
    @GetMapping("/get/{aid}")
    public ResultMap deleteAds(@PathVariable("aid") Long id){
        ResultMap resultMap = adsService.selectById(id);
        return resultMap;
    }

}
