package com.scaudachuang.campus_navigation.service;

import com.scaudachuang.campus_navigation.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    void addUser(User user);
    void deleteUserById(int id);
    User findUserById(int id);
}
