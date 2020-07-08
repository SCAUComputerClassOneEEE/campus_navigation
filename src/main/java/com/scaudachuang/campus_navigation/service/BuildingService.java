package com.scaudachuang.campus_navigation.service;

import com.scaudachuang.campus_navigation.entity.Building;
import javafx.collections.ObservableList;

import java.util.List;

public interface BuildingService {
    Building getBuildingById(int id);
    List<Building> finAll();
}
