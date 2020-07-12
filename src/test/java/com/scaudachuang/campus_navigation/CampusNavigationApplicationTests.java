package com.scaudachuang.campus_navigation;


import com.alibaba.fastjson.JSONArray;
import com.scaudachuang.campus_navigation.entity.Admin;
import com.scaudachuang.campus_navigation.entity.Comment;
import com.scaudachuang.campus_navigation.fx.model.DataEnum;
import com.scaudachuang.campus_navigation.fx.model.DataTab;
import com.scaudachuang.campus_navigation.service.AdminService;
import com.scaudachuang.campus_navigation.service.CommentService;
import com.scaudachuang.campus_navigation.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    void iocTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        Page<Comment> commentList = commentService.findByPage(0,3,1);
//        System.out.println(commentList.getSize());
//        for (Comment comment : commentList){
//            System.out.println(comment.getBuilding().getName());
//        }

    Admin admin = adminService.findAdminByAdminName("sky");
    Object o = admin.getClass();
        System.out.println(o.toString());
    Method m = admin.getClass().getMethod("getId");
        System.out.println(m.invoke(admin).toString());
    }
}
