package com.feel.id;

import java.util.UUID;

/**
 * Created by yulong.li on 2017/9/13.
 */
public class UUidTest {
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
        System.out.println(UUID.randomUUID().toString().replaceAll("-","--"));
    }
}
