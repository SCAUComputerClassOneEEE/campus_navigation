package com.scaudachuang.campus_navigation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "user")
public class User {

    @Id
    private int openId;

    /**
     * 自定义登陆态"随机码"，方便于评论者信息的读取。
     */
    @Column(name = "defined_log_status")
    private String definedLoginStatus;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_info")
    private String userInfo;

    @Column(name = "curr_log_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date currLogTime;

    @Column(name = "reg_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date regTime;

}
