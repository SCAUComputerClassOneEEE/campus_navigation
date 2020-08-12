package com.scaudachuang.campus_navigation.controller;

import com.scaudachuang.campus_navigation.entity.Admin;
import com.scaudachuang.campus_navigation.entity.Comment;
import com.scaudachuang.campus_navigation.entity.User;
import com.scaudachuang.campus_navigation.service.AdminService;
import com.scaudachuang.campus_navigation.service.BuildingService;
import com.scaudachuang.campus_navigation.service.CommentService;
import com.scaudachuang.campus_navigation.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;

@RestController
@RequestMapping("/affair")
public class AffairController {

    @GetMapping(value = "/getNotice")
    public String notice(){
        File file = new File(System.getProperty("user.dir") +"\\src\\main\\resources\\static\\notice");
        return "notice";
    }

    @PostMapping(value = "/reportedIt")
    public void reportedComment(@RequestBody Comment reportedComment){
        System.out.println(reportedComment.getMessage());
    }
}
