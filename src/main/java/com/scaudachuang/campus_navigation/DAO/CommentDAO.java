package com.scaudachuang.campus_navigation.DAO;

import com.scaudachuang.campus_navigation.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDAO extends JpaRepository<Comment,Integer>, JpaSpecificationExecutor<Comment> {

}
