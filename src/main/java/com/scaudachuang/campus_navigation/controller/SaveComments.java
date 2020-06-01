package com.scaudachuang.campus_navigation.controller;

import com.scaudachuang.campus_navigation.entity.Comment;
import com.scaudachuang.campus_navigation.service.CommentService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/comment")
public class SaveComments {

    @Resource
    private CommentService commentService;

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String saveComments(@RequestBody Comment comment){

        String response = "Message: ";
        try{
            comment.setTimeOfCommentary(new Date());
            commentService.save(comment);
            response += "Done!";
        }catch (Exception e){
            response = e.getMessage();
        }

        return response;
    }
}
