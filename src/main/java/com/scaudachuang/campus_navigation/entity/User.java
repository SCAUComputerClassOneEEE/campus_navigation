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
@Entity(name ="user")
@Table(name = "user")
public class User {

    @Id
    private int id;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "session_key")
    private String sessionKey;
    /**
     * 自定义登陆态"随机码"，方便于评论者信息的读取。
     */

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
