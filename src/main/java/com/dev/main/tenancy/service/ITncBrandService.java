package com.dev.main.tenancy.service;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.domain.TncBrand;

import java.util.List;

public interface ITncBrandService {

    /**
     * 删除品牌信息
     * @param id
     * @return
     */
    int deleteBrand(Long id);

    /**
     * 添加品牌信息
     * @param record
     * @return
     */
    int addBrand(TncBrand record);

    /**
     * 根据Id查找相应品牌
     * @param id
     * @return
     */
    TncBrand findByPrimaryKey(Long id);

    /**
     * 修改品牌信息 以及 删除后台显示数据,即置is_delete为1
     * @param record
     * @return
     */
    int modifiedByPrimaryKeySelective(TncBrand record);

    /**
     * 查询一页数据
     * @param queryObject
     * @return
     */
    Page queryByPage(QueryObject queryObject);

    /**
     * 根据Id 和 Name 查重
     * @param record
     * @return
     */
    TncBrand findByName(TncBrand record);

    List<TncBrand> getBandList();
}
