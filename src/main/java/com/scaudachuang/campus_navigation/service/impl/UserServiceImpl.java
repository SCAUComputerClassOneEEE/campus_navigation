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
    public User findByOpenId(String openId) {
        return userDAO.findByOpenId(openId);
    }

    @Override
    public void updateUserByOpenId(String openId, JSONObject jsonObject) {
        User newUser = userDAO.findByOpenId(openId);
        newUser.setCurrLogTime(new Date());
        newUser.setUserName(jsonObject.getString("nickName"));
        userDAO.save(newUser);
    }

    @Override
    public String insertRegUser(JSONObject userInfo,String openId,String sessionKey) {
        String definedLoginStatus = RandomString.getRandomString(10);
        String user_name;
        User insert_user = new User();

        insert_user.setOpenId(openId);
        insert_user.setSessionKey(sessionKey);
        insert_user.setRegTime(new Date());
        insert_user.setCurrLogTime(new Date());
        insert_user.setDefinedLoginStatus(definedLoginStatus);
        insert_user.setUserInfo(userInfo.toJSONString());
        insert_user.setUserName(userInfo.getString("nickName"));

        userDAO.save(insert_user);
        user_name = insert_user.getUserName();
        return user_name + " " + definedLoginStatus;
    }
}
