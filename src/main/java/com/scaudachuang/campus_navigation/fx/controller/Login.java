package com.scaudachuang.campus_navigation.fx.controller;

import com.scaudachuang.campus_navigation.entity.Admin;
import com.scaudachuang.campus_navigation.service.AdminService;

import javax.annotation.Resource;

public class Login {

    @Resource
    private AdminService adminService;

    public boolean login(String adminName,String password){
        Admin admin = adminService.findAdminByAdminName(adminName);
        return true;
    }
}
