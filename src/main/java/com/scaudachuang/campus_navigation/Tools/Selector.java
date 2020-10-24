package com.scaudachuang.campus_navigation.Tools;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 从文件夹中读取图片并将图片加入Buffer队列
 */
public class Selector implements Runnable{

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("Selector start...");
        File buildingDirectory = new File(String.valueOf(this.getClass().getClassLoader().getResource("buildings")).substring(6));
        File[] buildingFiles = buildingDirectory.listFiles();

        assert buildingFiles != null;
        ArrayList<File> fileArrayList = new ArrayList<>(Arrays.asList(buildingFiles));

        BufferQueue bufferQueue = BufferQueue.getBufferQueue();

        //生产
        while (fileArrayList.size()>0){
            if (bufferQueue.add(fileArrayList.get(0))){
                fileArrayList.remove(0);
            }
        }
    }
}
