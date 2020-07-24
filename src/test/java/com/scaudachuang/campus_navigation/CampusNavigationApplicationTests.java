package com.scaudachuang.campus_navigation;


import com.scaudachuang.campus_navigation.entity.Admin;
import com.scaudachuang.campus_navigation.fx.model.DataEnum;
import com.scaudachuang.campus_navigation.service.AdminService;
import com.scaudachuang.campus_navigation.service.BuildingService;
import com.scaudachuang.campus_navigation.service.CommentService;
import com.scaudachuang.campus_navigation.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


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
    void iocTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchFieldException {
//        Page<Comment> commentList = commentService.findByPage(0,3,1);
//        System.out.println(commentList.getSize());
//        for (Comment comment : commentList){
//            System.out.println(comment.getBuilding().getName());
//        }
        Class<?> c = Class.forName("com.scaudachuang.campus_navigation.entity.Admin");
        Object o = c.newInstance();
        //获取名字
        System.out.println(c.getSimpleName());

        //获取属性
        Field field = c.getDeclaredField("password");
        String name = field.getName();
        //生成方法名字
        String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        System.out.println(methodName);

        //获取方法
        Method method = c.getMethod(methodName, field.getType());
        //输入实例，执行方法
        method.invoke(o,"11");
        Admin admin = (Admin)o;
        System.out.println(admin.getPassword());
    }
}
