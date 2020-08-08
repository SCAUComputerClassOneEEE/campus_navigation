package com.scaudachuang.campus_navigation.controller;

import com.scaudachuang.campus_navigation.entity.Comment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/affair")
public class AffairController {

    @PostMapping(value = "/getNotice")
    public String notice(){
        return "notice";
    }

    @PostMapping(value = "/reportedIt")
    public void reportedComment(@RequestBody Comment comment){

        System.out.println(comment);
    }
}
