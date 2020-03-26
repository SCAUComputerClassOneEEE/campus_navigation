package com.scaudachuang.campus_navigation.POJO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/*
参数接受包装成POJO
 */
@Getter
@Setter
public class ImageFromWx {
    private MultipartFile img;
}
