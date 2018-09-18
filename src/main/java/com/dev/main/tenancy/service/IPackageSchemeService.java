package com.dev.main.tenancy.service;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.domain.TncPackageScheme;

public interface IPackageSchemeService {
    ResultMap deleteByPrimaryKey(Long id);

    /**
     * 添加套餐
     * @param tps 套餐domain
     * @return ResultMap
     */
    ResultMap insert(TncPackageScheme tps);

    int insertSelective(TncPackageScheme record);

    TncPackageScheme selectByPrimaryKey(Long id);

    /**
     * 编辑套餐
     * @param record 套餐domain
     * @return ResultMap
     */
    ResultMap updateByPrimaryKeySelective(TncPackageScheme record);

    int updateByPrimaryKey(TncPackageScheme record);

    /**
     * 查询套餐名是否已存在
     * @param record 套餐
     * @return
     */
    ResultMap selectPackageSchemeNmae(TncPackageScheme record);

    Page listPackageScheme(QueryObject queryObject);
}
