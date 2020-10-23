package com.scaudachuang.campus_navigation.Tools;

import com.scaudachuang.campus_navigation.entity.Building;
import org.bouncycastle.util.encoders.Base64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

public class TakConsumer implements Runnable{

    @Override
    public void run() {

        BufferQueue bufferQueue = BufferQueue.getBufferQueue();

        while (true){
            BufferQueue.FileKV fileKV = bufferQueue.take();
            if (fileKV != null){
                Building building = new Building();
                building.setId(fileKV.getId());
                building.setName(fileKV.getBuildingName());

                Base64Encoder base64Encoder = new Base64Encoder();

            }
        }
    }

    private byte[] readFile(File file){
        try(FileInputStream fi = new FileInputStream(file)){
           byte[] b = new byte[fi.available()];
           int readLen = fi.read(b);
           if (readLen==fi.available())
               return b;
           return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
