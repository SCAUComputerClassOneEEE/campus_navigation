package com.scaudachuang.campus_navigation.service.impl;

import com.scaudachuang.campus_navigation.DAO.BuildingDAO;
import com.scaudachuang.campus_navigation.entity.Building;
import com.scaudachuang.campus_navigation.service.BuildingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Resource
    private BuildingDAO buildingDAO;

    @Override
    public Building getBuildingById(int id) {
        return buildingDAO.findBuildingById(id);
    }

    @Override
    public List<Building> finAll() {
        return buildingDAO.findAll();
    }

    @Override
    public void deleteBuildingById(int id) { buildingDAO.deleteById(id); }

    @Override
    public void addBuilding(Building building) {
        buildingDAO.save(building);
    }

}
