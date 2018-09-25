package com.dev.main.tenancy.controller;

import com.dev.main.common.controller.exception.GlobalExceptionResolver;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.domain.TncPriceScheme;
import com.dev.main.tenancy.service.ITncPriceSchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
