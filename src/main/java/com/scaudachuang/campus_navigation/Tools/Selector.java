package com.scaudachuang.campus_navigation.Tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 从文件夹中读取图片并将图片加入Buffer队列
 */
public class Selector implements Runnable{

    @Override
    public void run() {
        //获取文件
        String buildingDirectoryPath = "buildings";
        File buildingDirectory = new File(String.valueOf(getClass().getResource(buildingDirectoryPath)));
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
