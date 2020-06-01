package com.scaudachuang.campus_navigation.controller;

import com.scaudachuang.campus_navigation.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/login")
public class LoginController  {

    @Resource
    private UserService userService;

}
