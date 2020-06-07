package com.scaudachuang.campus_navigation.DAO;

import com.scaudachuang.campus_navigation.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDAO extends JpaRepository<Admin,Integer> {
    Admin findAdminByAdminName(String adminName);
}
