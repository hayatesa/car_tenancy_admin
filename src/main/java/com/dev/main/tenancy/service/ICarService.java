package com.dev.main.tenancy.service;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.domain.TncCar;

public interface ICarService {
    Page getCarList(QueryObject queryObject);

    int addCar(TncCar tncCar);

    TncCar getCarByCarId(Long carId);

    int deleteCarByCarId(Long carId);

    int updateCar(TncCar tncCar);
}
