package com.scaudachuang.campus_navigation;


import com.alibaba.fastjson.JSONArray;
import com.scaudachuang.campus_navigation.entity.Admin;
import com.scaudachuang.campus_navigation.entity.Comment;
import com.scaudachuang.campus_navigation.service.AdminService;
import com.scaudachuang.campus_navigation.service.CommentService;
import com.scaudachuang.campus_navigation.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
class CampusNavigationApplicationTests {

    @Resource
    private UserService userService;
    @Resource
    private CommentService commentService;

    @Resource
    private AdminService adminService;

    @Test
    void iocTest(){
//        Page<Comment> commentList = commentService.findByPage(0,3,1);
//        System.out.println(commentList.getSize());
//        for (Comment comment : commentList){
//            System.out.println(comment.getBuilding().getName());
//        }
        Admin admin = adminService.findAdminByAdminName("sky");
        String password = "000001";
        if (admin.getPassword().equals(password)) System.out.println("yes");
        else System.out.println("error");
    }
}
