package com.scaudachuang.campus_navigation.service.impl;

import com.scaudachuang.campus_navigation.DAO.BuildingDAO;
import com.scaudachuang.campus_navigation.entity.Building;
import com.scaudachuang.campus_navigation.service.BuildingService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Getter
@Setter
@Service
public class BuildingServiceImpl implements BuildingService {

    @Resource
    private BuildingDAO buildingDAO;

    @Override
    public Building getBuildingById(int id) {
        return buildingDAO.getOne(id);
    }

}
