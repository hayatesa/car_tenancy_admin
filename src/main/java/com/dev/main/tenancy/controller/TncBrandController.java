package com.dev.main.tenancy.controller;

import com.alibaba.fastjson.JSONObject;
import com.dev.main.common.statics.StatusCode;
import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.shiro.controller.exception.ShiroExceptionResolver;
import com.dev.main.tenancy.domain.TncBrand;
import com.dev.main.tenancy.service.ITncBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@ShiroExceptionResolver
@RequestMapping("/api/tncBrand")
public class TncBrandController {

    @Autowired
    private ITncBrandService iTncBrandService;

    public void setiTncBrandService(ITncBrandService iTncBrandService) {
        this.iTncBrandService = iTncBrandService;
    }

    /**
     * 品牌列表
     *
     * @param page
     * @param limit
     * @param search
     * @param orderField
     * @param orderType
     * @return
     */
    @RequestMapping("/list")
    public Page BrandList(@RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer limit,
                          @RequestParam(required = false) String search,
                          @RequestParam(required = false) String orderField,
                          @RequestParam(required = false) String orderType) {
        QueryObject queryObject = new QueryObject(page, limit, search, orderField, orderType);
        return iTncBrandService.queryByPage(queryObject);
    }

    /**
     * 添加品牌
     *
     * @param data
     * @return
     */
    @RequestMapping("/add")
    public ResultMap BrandAdd(@RequestBody String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        TncBrand tncBrand = new TncBrand();
        tncBrand.setName(jsonObject.getString("name"));
        tncBrand.setIsDeleted((byte) 0);
        Date d = new Date();
        tncBrand.setGmtCreate(d);
        tncBrand.setGmtModified(d);
        int result = iTncBrandService.addBrand(tncBrand);
        ResultMap resultMap = new ResultMap();
        if (result > 0) {
            resultMap.put("msg", "添加成功");
            resultMap.put("code", StatusCode.SUCCESS);
        } else {
            resultMap.put("msg", "添加失败");
            resultMap.put("code", "-1");
        }
        return resultMap;
    }

    /**
     * 编辑品牌
     *
     * @param data
     * @return
     */
    @RequestMapping("/edit")
    public ResultMap BrandModified(@RequestBody String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        TncBrand tncBrand = new TncBrand();
        tncBrand.setId(jsonObject.getLong("id"));
        tncBrand.setName(jsonObject.getString("name"));
        Date d = new Date();
        tncBrand.setGmtModified(d);
        int result = iTncBrandService.modifiedByPrimaryKeySelective(tncBrand);
        ResultMap resultMap = new ResultMap();
        if (result > 0) {
            resultMap.put("msg", "编辑成功");
            resultMap.put("code", StatusCode.SUCCESS);
        } else {
            resultMap.put("msg", "编辑失败");
            resultMap.put("code", "-1");
        }
        return resultMap;
    }

    /**
     * 删除品牌
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public ResultMap BrandDelete(@RequestParam Long id) {
        TncBrand tncBrand = new TncBrand();
        tncBrand.setId(id);
        tncBrand.setIsDeleted((byte) 1);
        Date d = new Date();
        tncBrand.setGmtModified(d);
        int result = iTncBrandService.modifiedByPrimaryKeySelective(tncBrand);
        ResultMap resultMap = new ResultMap();
        if (result > 0) {
            resultMap.put("msg", "删除成功");
            resultMap.put("code", StatusCode.SUCCESS);
        } else {
            resultMap.put("msg", "删除失败");
            resultMap.put("code", "-1");
        }
        return resultMap;
    }

    /**
     * 判断品牌是否存在
     *
     * @param data
     * @return
     */
    @RequestMapping("/find")
    public ResultMap BrandFind(@RequestBody String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        TncBrand tncBrand = new TncBrand();
        tncBrand.setId(jsonObject.getLong("id"));
        tncBrand.setName(jsonObject.getString("name"));
        TncBrand tncBrand2 = iTncBrandService.findByName(tncBrand);
        ResultMap resultMap = new ResultMap();
        if (tncBrand2 != null) {
            resultMap.put("msg", "品牌存在");
            resultMap.put("code", StatusCode.SUCCESS);
        } else {
            resultMap.put("msg", "品牌不存在");
            resultMap.put("code", "-1");
        }
        return resultMap;
    }
}
