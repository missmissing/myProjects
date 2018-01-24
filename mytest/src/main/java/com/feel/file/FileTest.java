package com.feel.file;

import java.net.URL;
import java.util.Objects;

/**
 * Created by yulong.li on 2017/7/11.
 */
public class FileTest {
    public static void main(String[] args) {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(path);

        File2 file2 = new File2();
        file2.test();
    }
}

class File2{

    public void test(){
        String classesPath = this.getClass().getClassLoader().getResource("").getPath();
        System.out.println(classesPath);

        String classesPath2 = Objects.requireNonNull(this.getClass().getClassLoader().getResource("")).getPath();
        System.out.println(classesPath2);

        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(path);
    }
}
