package com.scaudachuang.campus_navigation.service;

import com.scaudachuang.campus_navigation.entity.Building;

import java.util.List;

public interface BuildingService {
    Building getBuildingById(int id);
    List<Building> finAll();
    void deleteBuildingById(int id);
    void addBuilding(Building building);
}
