package com.feel.collection.setoperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 交集运算
 * Created by yulong.li on 2017/9/8.
 */
public class SetOperationTest {
    public static void main(String[] args) {
        List<Material> list1 = new ArrayList<>(Arrays.asList(
                new Material(1,"a","一份"),
                new Material(2,"a","两份"),
                new Material(3,"b","一份")));

        List<Material> list2 = new ArrayList<>(Arrays.asList(
                new Material(4,"a","三份"),
                new Material(5,"a","四份"),
                new Material(6,"c","一份")));

        List<Material> list3 = new ArrayList<>();

        list1.retainAll(list2);
        list2.retainAll(list1);

        list1.stream().forEach(m -> System.out.println(m.toString()));
        list2.stream().forEach(m -> System.out.println(m.toString()));

        System.out.println("--");

        list1.retainAll(list3);
        list1.stream().forEach(m -> System.out.println(m.toString()));
    }
}
