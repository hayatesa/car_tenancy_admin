package com.dev.main.tenancy.service.impl;

import com.dev.main.tenancy.dao.TncCarPicMapper;
import com.dev.main.tenancy.domain.TncCarPic;
import com.dev.main.tenancy.service.ITncCarPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TncCarPicServiceImpl implements ITncCarPicService {
    @Autowired
    private TncCarPicMapper tncCarPicMapper;

    @Override
    public int storagePic(TncCarPic tncCarPic) {
        tncCarPic.setGmtCreate(new Date());
        tncCarPic.setGmtModified(new Date());
        tncCarPic.setIsDeleted(new Byte("0"));
        return tncCarPicMapper.insertSelective(tncCarPic);
    }
}
