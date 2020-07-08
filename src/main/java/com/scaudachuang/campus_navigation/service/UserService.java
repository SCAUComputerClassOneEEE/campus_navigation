package com.scaudachuang.campus_navigation.service;

import com.alibaba.fastjson.JSONObject;
import com.scaudachuang.campus_navigation.entity.User;

import java.util.List;

public interface UserService {
    User findByOpenId(String openId);
    void updateUserByOpenId(String openId, JSONObject jsonObject);
    String insertRegUser(JSONObject jsonObject,String openId,String sessionKey);
    List<User> findAll();
    void deleteUsers(List<User> userList);
}
