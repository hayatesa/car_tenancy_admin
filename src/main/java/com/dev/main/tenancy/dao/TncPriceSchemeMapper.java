package com.dev.main.tenancy.dao;

import com.dev.main.tenancy.domain.TncPriceScheme;
import com.dev.main.tenancy.vo.TncCarPriceVo;

import java.util.List;

public interface TncPriceSchemeMapper extends BaseMapper<TncPriceScheme> {
    int deleteByPrimaryKey(Long id);

    int insert(TncPriceScheme record);

    int insertSelective(TncPriceScheme record);

    TncPriceScheme selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TncPriceScheme record);

    int updateByPrimaryKey(TncPriceScheme record);

    List<TncCarPriceVo> selectCarPriceList(Integer carId);
}
