package com.scaudachuang.campus_navigation;


import com.scaudachuang.campus_navigation.Tools.Selector;
import com.scaudachuang.campus_navigation.Tools.TaskConsumer;
import com.scaudachuang.campus_navigation.entity.Admin;
import com.scaudachuang.campus_navigation.service.AdminService;
import com.scaudachuang.campus_navigation.service.BuildingService;
import com.scaudachuang.campus_navigation.service.CommentService;
import com.scaudachuang.campus_navigation.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Base64;
import java.util.Objects;


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

    @Test
    void getResourcesTest(){
        System.out.println(getClass().getResource("/file/message.txt"));
        URL url = getClass().getResource("/file/message.txt");
        String filePath = url.toString().substring(6);
        System.out.println(filePath);
        File file = new File("D:/sources/java/campus_navigation/target/test-classes/file/message.txt");
        System.out.println(file.exists());
    }

    @Test
    public void tool() throws InterruptedException, IOException {
//        for (int i = 1;i<=56;i++){
//            buildingService.deleteBuildingById(i);
//        }
        long sTime = System.currentTimeMillis();
        Thread t1 = new Thread(new Selector());
        Thread t2 = new Thread(new TaskConsumer(buildingService));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        long eTime = System.currentTimeMillis();
        System.out.println(eTime - sTime);
    }

    @Test
    public void base64test(){
        String fileName = Objects.requireNonNull(this.getClass().getClassLoader().getResource("1.JPG")).toString().substring(6);
        System.out.println(fileName);
        File file = new File(fileName);
        String s = Base64.getEncoder().encodeToString(TaskConsumer.readFile(file));
        System.out.println(s.length());
        System.out.println(s);
    }

}
