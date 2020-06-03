package com.scaudachuang.campus_navigation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.scaudachuang.campus_navigation.DAO.UserDAO;
import com.scaudachuang.campus_navigation.entity.User;
import com.scaudachuang.campus_navigation.service.UserService;
import com.scaudachuang.campus_navigation.utils.code.RandomString;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDAO userDAO;
    @Override
    public User findByOpenId(int openId) {
        return userDAO.getOne(openId);
    }

    @Override
    public void updateUserByOpenId(int openId, JSONObject jsonObject) {
        User newUser = userDAO.getOne(openId);
        newUser.setCurrLogTime(new Date());
        newUser.setUserName(jsonObject.getString("nickName"));
        userDAO.save(newUser);
    }

    @Override
    public void updateUserByOpenId(int openId, String nickName) {
        User newUser = userDAO.getOne(openId);
        newUser.setCurrLogTime(new Date());
        newUser.setUserName(nickName);
        userDAO.save(newUser);
    }

    @Override
    public String insertRegUser(JSONObject userInfo) {
        User insert_user = new User();
        insert_user.setRegTime(new Date());
        insert_user.setCurrLogTime(new Date());
        insert_user.setDefinedLoginStatus(RandomString.getRandomString(10));
        insert_user.setUserInfo(userInfo.toJSONString());
        insert_user.setUserName(userInfo.getString("nickName"));
        userDAO.save(insert_user);
        return insert_user.getUserName();
    }
}
