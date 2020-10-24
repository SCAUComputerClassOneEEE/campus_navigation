package com.scaudachuang.campus_navigation.Tools;

import com.scaudachuang.campus_navigation.entity.Building;
import com.scaudachuang.campus_navigation.service.BuildingService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class TaskConsumer implements Runnable{

    private final BuildingService buildingService;

    private final AtomicInteger integer = new AtomicInteger();

    public TaskConsumer(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("Selector start...");

        if (buildingService == null) {
            System.out.println("buildingService == null False");
            return;
        }
        BufferQueue bufferQueue = BufferQueue.getBufferQueue();

        while (true){
            BufferQueue.FileKV fileKV = bufferQueue.take();
            if (fileKV != null){
                if (integer.get() == 56) break;
                Building building = new Building();

                building.setId(fileKV.getId());
                building.setName(fileKV.getBuildingName());
                building.setBriefIntroduction("null");
                building.setNumberOfBrowse(0);
                building.setNumberOfComment(0);
                building.setLatitude(fileKV.getLatitude());
                building.setLongitude(fileKV.getLongitude());

                byte[] read = readFile(fileKV.getFile());
                String encodeStr = Base64.getEncoder().encodeToString(read);
                building.setImg(encodeStr);
                System.out.println(integer.addAndGet(1));
//                System.out.println(buildingService);
                System.out.println(building.getName());
                buildingService.addBuilding(building);
            }
        }
    }

    public static byte[] readFile(File file){
        try(FileInputStream fi = new FileInputStream(file)){
            byte[] imgBuffer = new byte[fi.available()];
            fi.read(imgBuffer);
            return imgBuffer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
