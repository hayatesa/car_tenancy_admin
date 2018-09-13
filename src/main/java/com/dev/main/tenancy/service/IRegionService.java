package com.dev.main.tenancy.service;

import com.dev.main.common.util.Page;

import java.util.Map;

public interface IRegionService {

    Page queryByPage(Map<String, Object> queryParams);

}
