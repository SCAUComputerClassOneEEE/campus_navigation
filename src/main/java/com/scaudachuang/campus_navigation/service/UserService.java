package com.scaudachuang.campus_navigation.service;

import com.alibaba.fastjson.JSONObject;
import com.scaudachuang.campus_navigation.entity.User;

public interface UserService {
    User findByOpenId(int openId);
    void updateUserByOpenId(int openId, JSONObject jsonObject);
    void updateUserByOpenId(int openId, String nickName);
    String insertRegUser(JSONObject jsonObject);
}
