package com.scaudachuang.campus_navigation.fx.controller;

import com.scaudachuang.campus_navigation.entity.Admin;
import com.scaudachuang.campus_navigation.service.AdminService;
import de.felixroske.jfxsupport.FXMLController;

import javax.annotation.Resource;

@FXMLController
public class LoginController {

    @Resource
    private AdminService adminService;

    public boolean login(String adminName,String password){
        Admin admin = adminService.findAdminByAdminName(adminName);
        return true;
    }
}
