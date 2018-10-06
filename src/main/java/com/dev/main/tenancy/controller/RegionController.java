package com.dev.main.tenancy.controller;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.shiro.controller.exception.ShiroExceptionResolver;
import com.dev.main.tenancy.domain.AddressRegion;
import com.dev.main.tenancy.service.IRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ShiroExceptionResolver
@RequestMapping("/api/region")
public class RegionController {

    @Autowired
    private IRegionService regionService;

    @GetMapping("/list")
    public Page list(@RequestParam(required = false) Integer page,
                     @RequestParam(required = false) Integer limit,
                     @RequestParam(required = false) String search,
                     @RequestParam(required = false) String orderField,
                     @RequestParam(required = false) String orderType) {
        QueryObject queryObject = new QueryObject(page, limit, search, orderField, orderType);
        return regionService.queryByPage(queryObject);
    }
    /**
     * 根据门店查询城市列表
     * 返回结果：地址列表
     * @param id 该id为父级id
     * @param  level  地址级别 0-省 1-市 2-县
     *
     *  例： id=3 level=2  查询在河北省（id=3）下拥有门店的城市列表；
     *      id = 40,level=3 查询唐山市(id=40)下拥有门店的县地址列表
     * */
    @GetMapping("/addr")
    public ResultMap getAddressByStore(Long id, byte level ){
        List<AddressRegion> list =regionService.getAddressByStore(id,level);
        ResultMap map = new ResultMap();
        map.put("data",list);
        return map;
    }

    public void setRegionService(IRegionService regionService) {
        this.regionService = regionService;
    }
}
