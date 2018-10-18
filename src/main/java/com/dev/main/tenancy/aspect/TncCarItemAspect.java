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
            int n = carItemService.quantityAndResidualPlusOne(tncCarItem.getCarId());
    }

    @After(value = "execution(* com.dev.main.tenancy.service.impl.CarItemServiceImpl.deleteCarItem(..))")
    public void doSubQuantityAndResidual(JoinPoint joinPoint){
        Object[] ob = joinPoint.getArgs();
        TncCarItem tncCarItem = (TncCarItem) ob[0];
        System.out.println(tncCarItem.getStatus()+"status");
        if(tncCarItem.getStatus() == 0 ){
            int n = carItemService.quantityAndResidualSubOne(new Integer(tncCarItem.getId().toString()));
        }else {
            int n = carItemService.quantitySubOne(new Integer(tncCarItem.getId().toString()));
        }
    }

    @After(value = "execution(* com.dev.main.tenancy.service.impl.CarItemServiceImpl.deleteCarItemSubQ(..))")
    public void doSubQuantityBySubQ(JoinPoint joinPoint){
        Object[] ob = joinPoint.getArgs();
        TncCarItem tncCarItem = (TncCarItem) ob[0];
        System.out.println(tncCarItem.getStatus()+"status");
        int n = carItemService.quantitySubOne(new Integer(tncCarItem.getId().toString()));

    }

    @After(value = "execution(* com.dev.main.tenancy.service.impl.CarItemServiceImpl.updateCarItemStatus(..))")
    public void doSubResidual(JoinPoint joinPoint){
        Object[] ob = joinPoint.getArgs();
        Integer id  = (Integer) ob[0];
        Byte status  =  new Byte(ob[1].toString());
//        System.out.println(status);
        if(status == 0){
            int n = carItemService.residualAddOne(id);
        }else if(status ==2){
            int n = carItemService.residualSubOne(id);
        }else if(status ==3){
            int n = carItemService.residualSubOne(id);
        }

    }

    @After(value = "execution(* com.dev.main.tenancy.service.impl.CarItemServiceImpl.updateCarItemStatusSubQ(..))")
    public void doSubQuantity(JoinPoint joinPoint){
        Object[] ob = joinPoint.getArgs();
        Integer id  = (Integer) ob[0];
        Byte status  =  new Byte(ob[1].toString());
//        System.out.println(status);
        int n = carItemService.quantitySubOne(id);
    }
}
