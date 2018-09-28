package com.dev.main.tenancy.controller;

import com.alibaba.fastjson.JSONObject;
import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.shiro.controller.exception.ShiroExceptionResolver;
import com.dev.main.tenancy.dao.AddressRegionMapper;
import com.dev.main.tenancy.domain.TncAddress;
import com.dev.main.tenancy.domain.TncStore;
import com.dev.main.tenancy.service.ITncStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
    @GetMapping("/list")
    public Page StoreList(@RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer limit,
                          @RequestParam(required = false) String search,
                          @RequestParam(required = false) String orderField,
                          @RequestParam(required = false) String orderType) {
        QueryObject queryObject = new QueryObject(page, limit, search, orderField, orderType);
        return iTncStoreService.queryByPage(queryObject);
    }

    /**
     * 添加门店
     * @param tncStore
     * @return
     */
    @PostMapping("/add")
    public ResultMap StoreAdd(@RequestBody TncStore tncStore) {
        tncStore.setStatus((byte) 0);
        tncStore.setIsDeleted((byte) 0);
        Date d = new Date();
        tncStore.setGmtCreate(d);
        tncStore.setGmtModified(d);
        int result = iTncStoreService.addStore(tncStore);
        if(result < 0){
            return ResultMap.fail(-1,"添加失败");
        }
        return ResultMap.success("添加成功");
    }

    /**
     * 编辑门店信息
     * @param tncStore
     * @return
     */
    @PostMapping("/edit")
    public ResultMap StoreEdit(@RequestBody TncStore tncStore){
        tncStore.setGmtModified(new Date());
        int result = iTncStoreService.modifiedByPrimaryKeySelective(tncStore);
        if(result < 0){
            return ResultMap.fail(-1,"编辑失败");
        }
        return ResultMap.success("编辑成功");
    }

    /**
     * 删除门店信息，修改is_delete，数据库保留数据，后台无法查看
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public ResultMap deleteStore(Long id){
        TncStore tncStore = new TncStore();
        tncStore.setId(id);
        tncStore.setIsDeleted((byte) 1);
        tncStore.setGmtModified(new Date());
        int result = iTncStoreService.modifiedByPrimaryKeySelective(tncStore);
        if(result < 0){
            return ResultMap.fail(-1,"删除失败");
        }
        return ResultMap.success("删除成功");
    }

    /**
     * 修改状态 0-歇业 1-开业
     * @param tncStore
     * @return
     */
    @PostMapping("/storeStatus")
    public ResultMap storeStatus(TncStore tncStore){
        tncStore.setGmtModified(new Date());
        int result = iTncStoreService.modifiedByPrimaryKeySelective(tncStore);
        if(result < 0){
            return ResultMap.fail(-1,"状态修改失败");
        }
        return ResultMap.success("状态修改成功");
    }

    /**
     * 根据 Id 查找相应门店信息
     * @param id
     * @return
     */
    @GetMapping("/selectById")
    public ResultMap selectById(Long id){
        TncStore tncStore = iTncStoreService.findByPrimaryKey(id);
        ResultMap resultMap = new ResultMap();
        resultMap.put("data", tncStore);
        return resultMap;
    }

    /**
     * 根据地区Id查询门店名称列表
     * @param areaId  地区Id
     * */
    @GetMapping("/store")
    public ResultMap getStoreAddress(Integer areaId){
        ResultMap map = new ResultMap();
        List<TncStore> list = iTncStoreService.searchStoreList(areaId);
        map.put("data",list);
        return map;
    }

}
