package com.scaudachuang.campus_navigation.DAO;

import com.scaudachuang.campus_navigation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;

public interface UserDAO extends JpaRepository<User,Integer> {

    User findUserById(int id);
    @Transactional
    @Query(value = "update user set userName = ?1 where openId = ?2")
    @Modifying
    void updateUserNameByOpenId(String userName,String openId);

    @Transactional
    @Query(value = "update user set currLogTime = ?1 where openId = ?2")
    @Modifying
    void updateUserCurrLogTimeByOpenId(Date currLogTime, String openId);
}
