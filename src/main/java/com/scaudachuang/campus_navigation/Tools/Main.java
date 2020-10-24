package com.scaudachuang.campus_navigation.Tools;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        File file = new File(Objects.requireNonNull(Main.class.getClassLoader().getResource("buildings/1-电子工程学院.JPG")).toURI());
        byte[] read = TaskConsumer.readFile(file);
        String str = Base64.getEncoder().encodeToString(read);
        System.out.println(str.length());
    }
}
