package com.dev.main.tenancy.service;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.domain.TncStore;

import java.util.List;


public interface ITncStoreService {
    /**
     * 删除门店信息
     * @param id
     * @return
     */
    int deleteStore(Long id);

    /**
     * 添加门店信息
     * @param record
     * @return
     */
    int addStore(TncStore record);

    /**
     * 根据Id查找相应门店
     * @param id
     * @return
     */
    TncStore findByPrimaryKey(Long id);

    /**
     * 修改门店信息 以及 删除后台显示数据,即置is_delete为1
     * @param record
     * @return
     */
    int modifiedByPrimaryKeySelective(TncStore record);

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
    TncStore findByName(TncStore record);

    /**
     * 根据县/区id查门店
     * @param 地区Id
     * @param areaId*/
    List<TncStore> searchStoreList(Integer areaId);
}
