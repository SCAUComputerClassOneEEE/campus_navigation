package com.scaudachuang.campus_navigation.service;

import com.scaudachuang.campus_navigation.entity.Admin;

public interface AdminService {
    Admin findAdminByAdminName(String adminName);
}
