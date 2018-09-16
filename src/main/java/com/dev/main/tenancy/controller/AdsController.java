package com.dev.main.tenancy.controller;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
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
    public void addAds(TncAds tncAds){
        adsService.addAds(tncAds);
    }
    @PostMapping("/save")
    public void updateAds(TncAds tncAds){
        adsService.updateAds(tncAds);
    }
    @GetMapping("/del")
    public Object deleteAds(TncAds tncAds){
        return adsService.deleteAds(tncAds);
    }

}
