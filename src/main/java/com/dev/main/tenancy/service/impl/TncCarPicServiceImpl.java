package com.dev.main.tenancy.service.impl;

import com.dev.main.tenancy.dao.TncCarPicMapper;
import com.dev.main.tenancy.domain.TncCarPic;
import com.dev.main.tenancy.service.ITncCarPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Override
    public List<TncCarPic> getCarPic(byte carId) {
        return tncCarPicMapper.selectByCarId(carId);
    }

    @Override
    public int updatePic(TncCarPic tncCarPic) {

        return tncCarPicMapper.updateByPrimaryKeySelective(tncCarPic);
    }

    @Override
    public int deletePic(TncCarPic tncCarPic) {

        tncCarPic.setIsDeleted(new Byte("1"));
        tncCarPic.setIsCover(null);
        tncCarPic.setPath(null);
        tncCarPic.setGmtModified(new Date());
        tncCarPic.setGmtCreate(null);

        return tncCarPicMapper.updateByPrimaryKeySelective(tncCarPic);
    }
}
