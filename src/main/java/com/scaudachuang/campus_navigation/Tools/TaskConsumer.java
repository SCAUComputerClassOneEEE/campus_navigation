package com.scaudachuang.campus_navigation.Tools;

import com.scaudachuang.campus_navigation.entity.Building;
import lombok.SneakyThrows;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskConsumer implements Runnable{

    private final AtomicInteger integer = new AtomicInteger();

    @SneakyThrows
    @Override
    public void run() {

        BufferQueue bufferQueue = BufferQueue.getBufferQueue();

        while (true){
            BufferQueue.FileKV fileKV = bufferQueue.take();
            if (fileKV != null){
                Building building = new Building();
                building.setId(fileKV.getId());
                building.setName(fileKV.getBuildingName());

                byte[] read = readFile(fileKV.getFile());
                String encodeStr = Base64.getEncoder().encodeToString(read);
                building.setImg(encodeStr);
                System.out.println(building.toString());
                System.out.println(integer.addAndGet(1));
                if (integer.get() == 56) break;
//                System.out.println(buildingService);
//                buildingService.addBuilding(building);
            }
        }
    }

    private byte[] readFile(File file){
        try(FileInputStream fi = new FileInputStream(file)){
           byte[] b = new byte[fi.available()];
           return b;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
