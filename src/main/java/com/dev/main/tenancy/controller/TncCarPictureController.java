package com.dev.main.tenancy.controller;

import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.domain.TncCarPic;
import com.dev.main.tenancy.service.ITncCarPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carPic")
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
}
