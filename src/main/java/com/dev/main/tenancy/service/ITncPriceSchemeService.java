package com.dev.main.tenancy.service;

import com.dev.main.tenancy.domain.TncPriceScheme;
import com.dev.main.tenancy.vo.TncCarPriceVo;

import java.util.List;

public interface ITncPriceSchemeService {
    int addPriceScheme(TncPriceScheme tncPriceScheme);

    List<TncCarPriceVo> selectCarPriceList(Integer carId);

    int updatePrice(TncPriceScheme tncPriceScheme);

    int deletePrice(Long id);

    int batchDelete(String dataList);
}
