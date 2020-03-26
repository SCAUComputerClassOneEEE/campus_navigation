package com.scaudachuang.campus_navigation.DAO;

import com.scaudachuang.campus_navigation.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingDAO extends JpaRepository<Building,Integer> {
    Building getBuildingByName(String name);
}
