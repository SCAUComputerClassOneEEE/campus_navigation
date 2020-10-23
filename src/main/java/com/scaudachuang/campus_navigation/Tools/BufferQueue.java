package com.scaudachuang.campus_navigation.Tools;

import lombok.Data;

import java.io.File;
import java.util.List;
import java.util.Map;

public class BufferQueue {

    private static final BufferQueue bufferQueue = new BufferQueue();
    private static final int BUFFER_SIZE = 10;
    private final FileKV[] buffer = new FileKV[BUFFER_SIZE];
    private int head = 0;
    private int tail = 0;

    public static BufferQueue getBufferQueue(){
        return bufferQueue;
    }

    public boolean add(File file){
        String fileName = file.getName();
        String[] fileSplit = fileName.split("-");
        int id = Integer.parseInt(fileSplit[0]);
        String buildingName = fileSplit[1];
        synchronized (buffer) {
            if ((head + 1) % BUFFER_SIZE == tail){
                return false;
            }else{
                buffer[head] = new FileKV(file,id,buildingName);
                head = (head + 1) % BUFFER_SIZE;
            }
        }
        return true;
    }

    public FileKV take(){
        synchronized (buffer){
            return null;
        }
    }

    @Data
    public static class FileKV {
        private File file;
        private int id;
        private String buildingName;

        private FileKV(){

        }
        public FileKV(File file,int id,String buildingName){
            this.file = file;
            this.buildingName = buildingName;
            this.id = id;
        }
    }
}
