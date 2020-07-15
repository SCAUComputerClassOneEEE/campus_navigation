package com.scaudachuang.campus_navigation;


import com.scaudachuang.campus_navigation.service.AdminService;
import com.scaudachuang.campus_navigation.service.BuildingService;
import com.scaudachuang.campus_navigation.service.CommentService;
import com.scaudachuang.campus_navigation.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;


@SpringBootTest
class CampusNavigationApplicationTests {

    @Resource
    private UserService userService;
    @Resource
    private CommentService commentService;
    @Resource
    private BuildingService buildingService;
    @Resource
    private AdminService adminService;

    @Test
    void iocTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
//        Page<Comment> commentList = commentService.findByPage(0,3,1);
//        System.out.println(commentList.getSize());
//        for (Comment comment : commentList){
//            System.out.println(comment.getBuilding().getName());
//        }
    }
}
