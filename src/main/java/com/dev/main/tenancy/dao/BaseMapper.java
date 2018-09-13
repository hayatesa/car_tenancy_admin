package com.dev.main.tenancy.dao;

import com.dev.main.common.util.QueryObject;

import java.util.List;

public interface BaseMapper<T> {

    List<T> query(QueryObject queryObject);

}
