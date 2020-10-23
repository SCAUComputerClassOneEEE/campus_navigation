package com.scaudachuang.campus_navigation;

import com.scaudachuang.campus_navigation.entity.Building;
import com.scaudachuang.campus_navigation.service.BuildingService;
import com.sun.jmx.remote.internal.ArrayQueue;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.io.File;
import java.util.Queue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

public class AddBuildingTool {
    @Resource
    private BuildingService buildingService;
    //缓冲那些被筛选文件下的新的建筑id
    private final ArrayQueue<Integer> idQueue = new ArrayQueue<>(10);
    
    /**
     *
     * 先按id新建建筑records（无intro和 image BASE64编码，其他初始化为 0）
     * 然后resource下文件update数据库
     *
     * buildingImages文件下按id命名图片
     * buildingMessage文件下按id命名图片
     */
    final AtomicInteger taskCounter = new AtomicInteger(0);

    @Test
    public void addBuildingTool() {
        FutureTask<Boolean> encodeTask = new FutureTask<>( ()-> new AsyncTak().doTask());

    }

    String imageToStr(File imgFile) {
        return new String("");
    }

    class AsyncTak {
        Building target = new Building();

        public boolean doTask(){
            int curFileOrder;
            synchronized (taskCounter) {
                curFileOrder = taskCounter.decrementAndGet();
            }
            return true;
        }
    }
}
