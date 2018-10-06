package com.dev.main.tenancy.dao;

import com.dev.main.tenancy.domain.TncPackageScheme;

import java.util.List;

public interface TncPackageSchemeMapper extends BaseMapper<TncPackageScheme> {
    int deleteByPrimaryKey(Long id);

    int insert(TncPackageScheme record);

    int insertSelective(TncPackageScheme record);

    TncPackageScheme selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TncPackageScheme record);

    int updateByPrimaryKey(TncPackageScheme record);

    TncPackageScheme selectPackageSchemeByNmae(TncPackageScheme name);

    List<TncPackageScheme> getUnSelectPackage(byte carId);
}
