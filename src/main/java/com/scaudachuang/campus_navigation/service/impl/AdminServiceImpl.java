package com.scaudachuang.campus_navigation.service.impl;

import com.scaudachuang.campus_navigation.DAO.AdminDAO;
import com.scaudachuang.campus_navigation.entity.Admin;
import com.scaudachuang.campus_navigation.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminDAO adminDAO;

    @Override
    public Admin findAdminByAdminName(String adminName) {
        System.out.println(adminName);
        try{
            adminDAO.findAdminByAdminName(adminName);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return adminDAO.findAdminByAdminName(adminName);
    }
}
