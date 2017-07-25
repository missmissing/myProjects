package com.feel.optional;

/**
 * Created by yulong.li on 2017/7/18.
 */
public class NullTest {
    public static void main(String[] args) {
        int age = 0;
        System.out.println("user Age:" + age);

        long money;
        money = 10L;
        System.out.println("user money" + money);

        String name = null;
        System.out.println("user name:" + name);

        testNullObject();


    }

    public static void testNullObject() {
        if (null instanceof java.lang.Object) {
            System.out.println("null属于java.lang.Object类型");
        } else {
            System.out.println("null不属于java.lang.Object类型");
        }
    }

}
