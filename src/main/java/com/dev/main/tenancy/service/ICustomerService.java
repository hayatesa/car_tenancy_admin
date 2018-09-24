package com.dev.main.tenancy.service;

import com.alibaba.fastjson.JSONObject;
import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.domain.TncCustomer;
import com.dev.main.tenancy.vo.TncCustomerVo;

public interface ICustomerService {
    /**
     * 查询一页数据
     * @param queryObject
     * @return
     */
    Page queryByPage(QueryObject queryObject);

    /**禁用与解禁与删除*/
    void disable_delete(Long uid, int select);

    /**添加与保存*/
    void save(TncCustomer tncCustomer);

    /**单个查询*/
    TncCustomerVo findCustomerVo(Long uid);

    /**更改信息*/
    void changeInfo(TncCustomerVo tncCustomerVo);

}
