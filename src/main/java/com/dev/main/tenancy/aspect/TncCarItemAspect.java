package com.dev.main.tenancy.aspect;


import com.dev.main.tenancy.domain.TncCarItem;
import com.dev.main.tenancy.service.ICarItemService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class TncCarItemAspect {

    @Autowired
    private ICarItemService carItemService;

    /**
     * 添加车牌，车辆数加一，剩余数加一
     * */
    @After(value = "execution(* com.dev.main.tenancy.service.impl.CarItemServiceImpl.addCarItem(..))")
    public void doAddQuantityAndResidual(JoinPoint joinPoint){
            Object[] ob = joinPoint.getArgs();
            TncCarItem tncCarItem = (TncCarItem) ob[0];
            int n = carItemService.quantityPlusOne(tncCarItem.getCarId());
    }
}
