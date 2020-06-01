package com.scaudachuang.campus_navigation.service.impl;

import com.scaudachuang.campus_navigation.DAO.CommentDAO;
import com.scaudachuang.campus_navigation.entity.Comment;
import com.scaudachuang.campus_navigation.service.CommentService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    CommentDAO commentDAO;

    @Override
    public Page<Comment> findByPage(int page, int size, int buildingId) {
        return commentDAO.findAll((Specification<Comment>) (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicatesList = new ArrayList<>();
            //动态sql
            predicatesList.add(criteriaBuilder.equal(root.get("b_id"),buildingId));
            Predicate[] p = new Predicate[predicatesList.size()];
            return criteriaBuilder.and(predicatesList.toArray(p));

        }, PageRequest.of(page,size, Sort.Direction.DESC,"timeOfCommentary"));
    }

    public void save(Comment comment){
        commentDAO.save(comment);
    }
}
