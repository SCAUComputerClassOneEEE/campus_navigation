package com.scaudachuang.campus_navigation.Tools;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        File buildingDirectory = new File(String.valueOf(Main.class.getClassLoader().getResource("buildings")));
//        System.out.println(String.valueOf(Main.class.getClassLoader().getResource("buildings")));
        long sTime = System.currentTimeMillis();
        Thread t1 = new Thread(new Selector());
        Thread t2 = new Thread(new TaskConsumer());
        t1.start();
        t2.start();
        t2.join();
        long eTime = System.currentTimeMillis();
        System.out.println(eTime - sTime);
    }
}
