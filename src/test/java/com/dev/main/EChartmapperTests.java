package com.dev.main;

import com.dev.main.common.util.DateUtils;
import com.dev.main.tenancy.dao.CarRentIncomeMapper;
import com.dev.main.tenancy.vo.CarRentIncomeVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EChartmapperTests {

    @Autowired
    private CarRentIncomeMapper carRentIncomeMapper;

    public void setCarRentIncomeMapper(CarRentIncomeMapper carRentIncomeMapper) {
        this.carRentIncomeMapper = carRentIncomeMapper;
    }

    @Test
    public void testFindTopRent() {
        Map<String, Object> param = new HashMap<>();
        param.put("startDate", DateUtils.getDateByStr("2018-9-12"));
        param.put("endDate", DateUtils.getDateByStr("2018-10-12"));
        param.put("x", 10);
        List<CarRentIncomeVo> list = carRentIncomeMapper.findTopRent(param);
        System.out.println(param);
        System.out.println(list);
        System.out.println(list.size());
    }
}

