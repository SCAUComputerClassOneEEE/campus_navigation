package com.scaudachuang.campus_navigation.service;

import com.scaudachuang.campus_navigation.entity.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {
    Page<Comment> findByPage(int page,int size,int b_id);
    void save(Comment comment);
}
