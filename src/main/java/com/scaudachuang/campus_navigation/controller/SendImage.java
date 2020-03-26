package com.scaudachuang.campus_navigation.controller;

import com.scaudachuang.campus_navigation.POJO.ImageFromWx;
import com.scaudachuang.campus_navigation.entity.Building;
import com.scaudachuang.campus_navigation.service.BuildingService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/*
request小程序相机发来拍摄图片
return建筑实体类building
 */

@Getter
@Setter
@RestController
@RequestMapping("/sendImage")
public class SendImage {

    @Resource
    private BuildingService buildingService;

    /*
    id查询建筑api
     */
    @RequestMapping(value = "/getBuilding/{id}")
    public Building getBuilding(@PathVariable int id){

        return buildingService.getBuildingById(id);

    }

    /*
    上传图片得到建筑api
     */
    @RequestMapping(value = "/image",method = RequestMethod.POST)
    public Building getBuilding(@RequestParam("img")MultipartFile img){

        ImageFromWx imageFromWx = new ImageFromWx();
        imageFromWx.setImg(img);
        /*
        调用python计算
         */
        return buildingService.getBuildingById(1);

    }
}
