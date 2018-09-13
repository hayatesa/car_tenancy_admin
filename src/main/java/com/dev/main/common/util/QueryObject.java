package com.dev.main.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

public class QueryObject extends HashMap<String, Object> {

    private Integer page;
    private Integer limit;
    private String search;
    private String orderField;
    private String orderType;

    public QueryObject() {
        this(1, 10);
    }

    public QueryObject(Integer page, Integer limit) {
        this.page = page;
        this.limit = limit;
        super.put("page", page);
        super.put("limit", limit);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        super.put("page", page);
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        super.put("limit", limit);
        this.limit = limit;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        super.put("search", search);
        this.search = search;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        super.put("orderType", StringUtils.isBlank(orderField)? null : orderField);
        this.orderField = orderField;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        String type = orderType!=null && "desc".equalsIgnoreCase(orderType) ? "desc":"asc";
        super.put("orderType", type);
        this.orderType = orderType;
    }
}
