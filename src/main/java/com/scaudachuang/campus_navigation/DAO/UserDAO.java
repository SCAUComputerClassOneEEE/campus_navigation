package com.scaudachuang.campus_navigation.DAO;

import com.scaudachuang.campus_navigation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User,Integer> {
    User findByOpenId(String openId);
}
