package com.dev.main.tenancy.service;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;

public interface IRegionService {

    /**
     * 查询一页数据
     * @param queryObject
     * @return
     */
    Page queryByPage(QueryObject queryObject);

}
