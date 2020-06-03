package com.scaudachuang.campus_navigation.POJO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResult {
    private int status;
    private String definedLogStatus;
    private String msg;
    private String timestamp;

    public LoginResult(int status,String msg,String timestamp){
        this.msg = msg;
        this.status = status;
        this.timestamp = timestamp;
    }
    public LoginResult(int status,String msg,String timestamp,String definedLogStatus){
        this.msg = msg;
        this.status = status;
        this.timestamp = timestamp;
        this.definedLogStatus = definedLogStatus;
    }
}
