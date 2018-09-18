package com.dev.main.tenancy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dev.main.common.statics.StatusCode;
import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.shiro.controller.exception.ShiroExceptionResolver;
import com.dev.main.tenancy.domain.TncPackageScheme;
import com.dev.main.tenancy.service.IPackageSchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@ShiroExceptionResolver
@RequestMapping("/api/PackageScheme")
public class PackageSchemeController {

    @Autowired
    private IPackageSchemeService iPackageSchemeService;

    /**
     * 添加 id==null
     * 编辑 id!=null
     *
     * @param data PackageScheme
     * @return
     */
    @PostMapping("/save")
    public ResultMap savePackageScheme(@RequestBody String data) {
        JSONObject jps = JSONObject.parseObject(data);

        TncPackageScheme tps = new TncPackageScheme();
        tps.setId(jps.getLong("id"));
        tps.setName(jps.getString("name"));
        tps.setDaysMax(jps.getInteger("daysMax"));
        tps.setDaysMin(jps.getInteger("daysMin"));
        tps.setIsDeleted((byte) 0);
        tps.setGmtCreate(new Date());
        tps.setGmtModified(new Date());

        ResultMap result;
        if (jps.getLong("id") == null) {
            result = iPackageSchemeService.insert(tps);
            System.out.println("添加");
        } else {
            tps.setGmtCreate(null);
            result = iPackageSchemeService.updateByPrimaryKeySelective(tps);
            System.out.println("更新");
        }
        result.put("code", StatusCode.SUCCESS);
        System.out.println(result);
        return result;
    }

    /**
     * 检查套餐名是否重复
     *
     * @param data id && name
     * @return
     */
    @PostMapping("/select")
    public ResultMap selectPackageScheme(@RequestBody String data) {
        TncPackageScheme tps = new TncPackageScheme();
        JSONObject jps = JSONObject.parseObject(data);
        tps.setId(jps.getLong("id"));
        tps.setName(jps.getString("name"));
        ResultMap result;
        result = iPackageSchemeService.selectPackageSchemeNmae(tps);
        result.put("code", StatusCode.SUCCESS);
        return result;
    }

    /**
     * 根据 ID 删除套餐
     *
     * @param pid
     * @return
     */
    @PostMapping("/delete")
    public ResultMap deletePackageScheme(String pid) {
        ResultMap result;
        result = iPackageSchemeService.deleteByPrimaryKey(Long.valueOf(pid));
        result.put("code", StatusCode.SUCCESS);
        //return JSONObject.toJSONString(result);
        return result;
    }

    /**
     * list 套餐
     *
     * @param page
     * @param limit
     * @param search
     * @param orderField
     * @param orderType
     * @return
     */
    @GetMapping("/list")
    public Page listPackageScheme(@RequestParam(required = false) Integer page,
                                  @RequestParam(required = false) Integer limit,
                                  @RequestParam(required = false) String search,
                                  @RequestParam(required = false) String orderField,
                                  @RequestParam(required = false) String orderType) {

        QueryObject queryObject = new QueryObject(page, limit, search, orderField, orderType);

        return iPackageSchemeService.listPackageScheme(queryObject);
    }
}
