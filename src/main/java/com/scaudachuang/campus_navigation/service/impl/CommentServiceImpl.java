package com.scaudachuang.campus_navigation.service.impl;

import com.scaudachuang.campus_navigation.DAO.CommentDAO;
import com.scaudachuang.campus_navigation.entity.Comment;
import com.scaudachuang.campus_navigation.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    CommentDAO commentDAO;

//    @Override
//    public Page<Comment> findByPage(int page, int size, int bId,String sortKey) {
//        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,sortKey,"timeOfCommentary");
//        return commentDAO.findAll((Specification<Comment>) (root, criteriaQuery, criteriaBuilder) -> {
//
//            List<Predicate> predicatesList = new ArrayList<>();
//            //动态sql
//            predicatesList.add(criteriaBuilder.equal(root.get("bid"),bId));
//            Predicate[] p = new Predicate[predicatesList.size()];
//            return criteriaBuilder.and(predicatesList.toArray(p));
//
//        }, pageable);
//    }

    @Override
    public List<Comment> findAll() {
        return commentDAO.findAll();
    }

    @Override
    public void deleteCommentById(int id) {
        commentDAO.deleteById(id);
    }

    @Override
    public List<Comment> reportedComments() {
        return commentDAO.findAll().
                stream().
                filter(a -> a.getReports() != 0).
                sorted((o1, o2) -> o2.getReports() - o1.getReports()).
                collect(Collectors.toList());
    }

}
