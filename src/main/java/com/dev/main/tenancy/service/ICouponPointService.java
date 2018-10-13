package com.dev.main.tenancy.service;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.domain.TncBrand;
import com.dev.main.tenancy.domain.TncCouponPoint;

public interface ICouponPointService {
    /**
     * 添加品牌信息
     * @return
     */
    void addCouponPoint(TncCouponPoint tncCouponPoint);

    /**
     * 根据Id查找相应品牌
     * @param id
     * @return
     */
    TncCouponPoint findByPrimaryKey(Long id);

    /**
     * 修改品牌信息 以及 删除后台显示数据,即置is_delete为1
     * @param record
     * @return
     */
    void modifiedByPrimaryKeySelective(TncBrand record);

    /**
     * 查询一页数据
     * @param queryObject
     * @return
     */
    Page queryByPage(QueryObject queryObject);

    void disable(Long id, int select);
}
