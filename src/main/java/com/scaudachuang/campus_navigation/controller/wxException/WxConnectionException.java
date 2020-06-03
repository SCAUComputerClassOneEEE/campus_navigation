package com.scaudachuang.campus_navigation.controller.wxException;

public class WxConnectionException extends Exception{
    private final String msg;
    public WxConnectionException(String msg){
        this.msg = msg;
    }

    @Override
    public String getMessage(){
        return this.msg;
    }
}
