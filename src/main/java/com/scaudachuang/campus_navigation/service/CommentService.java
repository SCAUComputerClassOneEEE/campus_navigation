package com.scaudachuang.campus_navigation.service;

import com.scaudachuang.campus_navigation.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
    Page<Comment> findByPage(int page,int size,int bId,String sortKey);
    List<Comment> findByUid(int definedStatus);
    void addComment(Comment comment);
    List<Comment> findAll();
    void deleteComments(List<Comment> commentList);

}
