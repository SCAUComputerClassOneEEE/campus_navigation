package com.scaudachuang.campus_navigation;

import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @Author: Sky
 * @Date: 2020/10/23 9:22
 */
public class
kyTest {
    @Test
    public void t(){
        String buildingDirectoryPath = "/buildings";
        File buildingDirectory = new File(String.valueOf(getClass().getResource(buildingDirectoryPath)));
        File[] buildingFiles = buildingDirectory.listFiles();
        System.out.println(buildingFiles.length);
    }
}
