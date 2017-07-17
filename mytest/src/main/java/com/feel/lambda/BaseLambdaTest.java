package com.feel.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yulong.li on 2017/7/11.
 */
public class BaseLambdaTest {
    public static void main(String[] args) {

        /**
         * ->
         *
         */
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");

        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });

        Collections.sort(names,(o1, o2) -> o2.compareTo(o1));

        for (String s :
                names) {
            System.out.println(s);
        }
        /**
         * ::
         * Java 8 允许你使用 :: 关键字来传递方法或者构造函数引用，上面的代码展示了如何引用一个静态方法，我们也可以引用一个对象的方法
         */
        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        Integer converted = converter.convert("123");
        System.out.println(converted);    // 123

        Converter<String, Integer> converter2 = Integer::valueOf;
        Integer converted2 = converter2.convert("123");
        System.out.println(converted);   // 123



        /*对象的引用
        converter = String::startsWith;
        String converted = converter.convert("Java");
        System.out.println(converted);    // "J"*/

    }

    @FunctionalInterface
    interface Converter<F, T> {
        T convert(F from);
    }

}
