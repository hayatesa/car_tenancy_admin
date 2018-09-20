package com.dev.main.tenancy.service;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;

public interface ICarService {
    Page doSearch(QueryObject queryObject);
}
