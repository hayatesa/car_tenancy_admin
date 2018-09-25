package com.dev.main.tenancy.service.impl;

import com.dev.main.tenancy.dao.TncCarTypeMapper;
import com.dev.main.tenancy.domain.TncCarType;
import com.dev.main.tenancy.service.ITncCarTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TncCarTypeServiceImpl implements ITncCarTypeService {

    @Autowired
    private TncCarTypeMapper tncCarTypeMapper;
    @Override
    public List<TncCarType> getCarTypeList() {


        return tncCarTypeMapper.selectAllCarType();
    }
}
