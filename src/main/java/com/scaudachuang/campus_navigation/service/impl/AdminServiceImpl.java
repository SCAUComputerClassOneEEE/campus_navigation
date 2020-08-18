package com.scaudachuang.campus_navigation.service.impl;

import com.scaudachuang.campus_navigation.DAO.AdminDAO;
import com.scaudachuang.campus_navigation.entity.Admin;
import com.scaudachuang.campus_navigation.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminDAO adminDAO;

    @Override
    public Admin findAdminByAdminName(String adminName) {
        return adminDAO.findAdminByAdminName(adminName);
    }

    @Override
    public List<Admin> findAll() {
        return adminDAO.findAll();
    }

    @Override
    public void addAdmin(Admin admin) {
        adminDAO.save(admin);
    }

    @Override
    public void deleteAdminById(int id) {
        adminDAO.deleteById(id);
    }

    @Override
    public Admin findAdminById(int id) {
        return adminDAO.findAdminById(id);
    }
}
