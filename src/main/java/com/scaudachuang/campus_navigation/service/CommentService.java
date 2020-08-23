package com.scaudachuang.campus_navigation.service;

import com.scaudachuang.campus_navigation.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAll();
    void deleteCommentById(int id);
    List<Comment> reportedComments();
}
