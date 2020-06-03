package com.scaudachuang.campus_navigation;

import com.scaudachuang.campus_navigation.entity.Comment;
import com.scaudachuang.campus_navigation.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
class CampusNavigationApplicationTests {

//    @Resource
//    CommentService commentService;
//
//    @Test
//    void contextLoads() {
//        List<Comment> list = commentService.findByPage(0,3,2).getContent();
//        for(Comment c:list)
//        System.out.println(c.getMessage());
//    }

    @Test
    void iocTest(){
        String user_name = "lyx";
        String defined = "skajhfdlasf";
        String string = user_name + " " + defined;
        String[] strings = string.split(" ");
        for (String s : strings){
            System.out.println(s);
        }

    }
}
