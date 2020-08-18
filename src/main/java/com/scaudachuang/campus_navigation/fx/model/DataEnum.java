package com.scaudachuang.campus_navigation.fx.model;


public class DataEnum {
    public enum DataForm {
        Admin,Building,Comment,User
    }
    public static String toUrl(DataForm form){
        return "com.scaudachuang.campus_navigation.entity." + form;
    }
}