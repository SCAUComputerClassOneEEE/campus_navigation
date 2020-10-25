package com.scaudachuang.campus_navigation.DAO;

import com.scaudachuang.campus_navigation.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface BuildingDAO extends JpaRepository<Building,Integer> {
    Building findBuildingById(int id);
    void deleteBuildingById(int id);

    @Transactional
    @Query(value = "update building set briefIntroduction = ?1 where id = ?2")
    @Modifying
    void updateBuildingIntroById(String intro, int id);
}
