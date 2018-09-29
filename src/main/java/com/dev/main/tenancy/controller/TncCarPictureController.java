package com.dev.main.tenancy.controller;

import com.dev.main.common.controller.exception.GlobalExceptionResolver;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.domain.TncCarPic;
import com.dev.main.tenancy.service.ITncCarPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/carPic")
@GlobalExceptionResolver
public class TncCarPictureController {

    @Autowired
    private ITncCarPicService tncCarPicService;

    /**
     * @param  path 路径
     * @param  carId 车型
     * @param  isCover 是否封面 0-非 1-是
     * */

    @GetMapping("/storage")
    public ResultMap storagePic(TncCarPic tncCarPic){
        int n = tncCarPicService.storagePic(tncCarPic);
        if (n>0){
            return ResultMap.success("上传成功");
        }else{
            return ResultMap.fail("上传失败");
        }
    }

    @GetMapping("/update")
    public ResultMap updatePic(TncCarPic tncCarPic){
        int n = tncCarPicService.updatePic(tncCarPic);
        if (n>0){
            return ResultMap.success("更新成功");
        }else{
            return ResultMap.fail("更新失败");
        }
    }

    @GetMapping("/delete")
    public ResultMap deletePic(TncCarPic tncCarPic){
        int n = tncCarPicService.deletePic(tncCarPic);
        if (n>0){
            return ResultMap.success("更新成功");
        }else{
            return ResultMap.fail("更新失败");
        }
    }


    @GetMapping("/get/{carId}")
    public ResultMap getCarPic(@PathVariable("carId") byte carId){
        ResultMap map = new ResultMap();
        List<TncCarPic> list = tncCarPicService.getCarPic(carId);
        map.put("count",list.size());
        map.put("data",list);
        return map;
    }
}
