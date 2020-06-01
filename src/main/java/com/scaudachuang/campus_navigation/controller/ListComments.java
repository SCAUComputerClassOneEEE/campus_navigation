package com.scaudachuang.campus_navigation.controller;

import com.scaudachuang.campus_navigation.entity.Comment;
import com.scaudachuang.campus_navigation.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

/*
小程序点击评论按钮，返回排好序的json数据
 */
@Slf4j
@RestController
@RequestMapping("/comment")
public class ListComments {

    @Resource
    private CommentService commentService;

    /*
    此方法用于前端，用户点击评论，查看目前建筑（已识别出来）的第几页评论内容。

    @param buildingId 建筑的主键（与comment表级联）作为查询的条件
    @param page 获取评论的第几页（从0开始）非必须，为0
    @param size 获取的评论列表的一页里包含多少条评论（至少为1）非必须，为1
    @param sortType 排序方式，按时间或热度，true = 时间，false = 热度
    @return comments 第page+1页包含size条的评论列表（json.按时间排序,新的在前面）
     */
    @RequestMapping(value = "/commentsDivideIntoPages",method = RequestMethod.GET)
    public List<Comment> commentsDivideIntoPages(
            @RequestParam(value = "buildingId")int buildingId,
            @RequestParam(value = "page",required = false,defaultValue = "0")int page,
            @RequestParam(value = "size",required = false,defaultValue = "1")int size,
            @RequestParam(value = "sortType",required = false,defaultValue = "true")boolean sortType){

        List<Comment> comments = commentService.findByPage(page,size,buildingId).getContent();
        //sort by comparator
        if(sortType){
            comments.sort(Comparator.comparing(c -> String.valueOf(c.getTimeOfCommentary())));
        }else{
            comments.sort(Comparator.comparing(c -> String.valueOf(c.getNumberOfPraise())));
        }
        return comments;

    }

}
