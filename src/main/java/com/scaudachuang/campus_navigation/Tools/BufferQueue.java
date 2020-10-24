package com.scaudachuang.campus_navigation.Tools;

import lombok.Data;

import java.io.*;

public class BufferQueue {

    private String[] ll = new String[56];
    private static BufferQueue bufferQueue;
    private static final int BUFFER_SIZE = 10;
    private final FileKV[] buffer = new FileKV[BUFFER_SIZE];
    private int head = 0;
    private int tail = 0;

    private BufferQueue() throws IOException {
        File buildingLL = new File(String.valueOf(this.getClass().getClassLoader().getResource("ll.txt")).substring(6));
        FileReader fileReader = new FileReader(buildingLL);
        BufferedReader br = new BufferedReader(fileReader);
        String temp;
        int i = 0;
        while ((temp = br.readLine()) != null){
            ll[i] = temp;
            i++;
        }
    }

    public static BufferQueue getBufferQueue() throws IOException {
        if (bufferQueue == null){
            synchronized (BufferQueue.class){
                if (bufferQueue == null)
                    bufferQueue = new BufferQueue();
            }
        }
        return bufferQueue;
    }

    public boolean add(File file){
        String fileName = file.getName().split("\\.")[0];
        String[] fileSplit = fileName.split("-");
        int id = Integer.parseInt(fileSplit[0]);
        String buildingName = fileSplit[1];
        String[] llStr = ll[id-1].split(",");
        double la = Double.parseDouble(llStr[0]);
        double lo =  Double.parseDouble(llStr[1]);

        synchronized (buffer) {
            if ((head + 1) % BUFFER_SIZE == tail){
                return false;
            }else{
                buffer[head] = new FileKV(file,id,la,lo,buildingName);
                head = (head + 1) % BUFFER_SIZE;
            }
        }
        return true;
    }

    public FileKV take(){
        synchronized (buffer){
            if (tail == head){
                //kong
                return null;
            }else {
                FileKV fileKV = buffer[tail];
                tail = (tail + 1) % BUFFER_SIZE;
                return fileKV;
            }
        }
    }

    @Data
    public static class FileKV {
        private File file;
        private int id;
        private double longitude;
        private double latitude;

        public FileKV(File file, int id, double longitude, double latitude, String buildingName) {
            this.file = file;
            this.id = id;
            this.longitude = longitude;
            this.latitude = latitude;
            this.buildingName = buildingName;
        }

        private String buildingName;

        private FileKV(){

        }
    }
}
