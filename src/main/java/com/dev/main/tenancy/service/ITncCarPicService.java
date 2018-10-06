package com.dev.main.tenancy.service;

import com.dev.main.tenancy.domain.TncCarPic;

import java.util.List;

public interface ITncCarPicService {
    int storagePic(TncCarPic tncCarPic);

    List<TncCarPic> getCarPic(byte carId);

    int updatePic(TncCarPic tncCarPic);

    int deletePic(TncCarPic tncCarPic);
}
