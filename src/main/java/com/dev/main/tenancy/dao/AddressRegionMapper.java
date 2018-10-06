package com.dev.main.tenancy.dao;

import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.domain.AddressRegion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressRegionMapper extends BaseMapper<AddressRegion> {
    int deleteByPrimaryKey(Long id);

    int insert(AddressRegion record);

    int insertSelective(AddressRegion record);

    AddressRegion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AddressRegion record);

    int updateByPrimaryKey(AddressRegion record);

    List<AddressRegion> searchAddress(@Param("id") Long id, @Param("level") byte level);

    List<AddressRegion> selectAddressByLevel(@Param("id")Long id, @Param("level") byte level);
}
