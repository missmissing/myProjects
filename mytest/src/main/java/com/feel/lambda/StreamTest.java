package com.feel.lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yulong.li on 2017/7/12.
 */
public class StreamTest {
    public static void main(String[] args) {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");

        stringCollection.stream().filter(s -> s.startsWith("a")).forEach(s -> System.out.println(s));
        stringCollection.stream().filter(s -> s.startsWith("a")).forEach(System.out::println);

        stringCollection.stream().filter(s -> s.startsWith("a")).sorted((o1, o2) -> o1.compareTo(o2)).forEach(System.out::println);

        stringCollection = null;
        stringCollection.stream().forEach(s->{});
    }
}
