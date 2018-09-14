package com.dev.main.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询对象
 * @author 溶酶菌
 */
public class QueryObject extends HashMap<String, Object> {

    private static final String PAGE = "page";
    private static final String LIMIT = "limit";
    private static final String SEARCH = "search";
    private static final String ORDER_FIELD = "orderFeild";
    private static final String ORDER_TYPE = "orderType";

    public QueryObject() {
        this(1, 10, null, null, null);
    }

    /**
     *
     * @param page 页码，从1开始
     * @param limit 页大小，默认为10
     */
    public QueryObject(Integer page, Integer limit) {
        this(page, limit, null, null, null);
    }

    /**
     *
     * @param page 页码，从1开始
     * @param limit 页大小，默认为10
     * @param search 搜索文本
     */
    public QueryObject(Integer page, Integer limit, String search) {
        this(page, limit, search, null, null);
    }

    /**
     *
     * @param page 页码，从1开始
     * @param limit 页大小，默认为10
     * @param search 搜索文本
     * @param orderField 排序字段，数据库中字段（列）名为snake格式，对象字段名为camel格式，注意转换
     * @param orderType 排序类型，“asc”或“desc”，如果不设置或者设置非法字段，则为默认值“asc”
     */
    public QueryObject(Integer page, Integer limit, String search, String orderField, String orderType) {
        this.put(PAGE, page == null || page < 1 ? 1 : page);
        this.put(LIMIT, limit == null || limit < 1 ? 10 : limit);
        this.put(SEARCH, StringUtils.isBlank(search)? null : search.trim());
        this.put(ORDER_FIELD, StringUtils.isBlank(orderField) ? null : orderField);
        this.put(ORDER_TYPE, StringUtils.isBlank(orderType) || !"desc".equalsIgnoreCase(orderType) ? "asc":"desc");
    }

    @Override
    public QueryObject put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
