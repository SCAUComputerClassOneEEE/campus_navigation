package com.scaudachuang.campus_navigation.service;

import com.scaudachuang.campus_navigation.entity.Admin;

import java.util.List;

public interface AdminService {
    Admin findAdminByAdminName(String adminName);
    List<Admin> findAll();
    void addAdmin(Admin admin);
    void deleteAdminById(int id);
    Admin findAdminById(int id);
}
