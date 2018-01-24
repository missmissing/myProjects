package com.feel.javabase.brace;

import java.util.ArrayList;
import java.util.List;

/**
 * 静态代码块儿:static 关键字 + 大括号,静态代码块儿在类初始化的时候执行一次
 * <p>
 * 实例化代码块儿:把静态代码块儿的static关键字去掉就是,实例化代码块儿在每次生成对象的时候都会执行(实例化代码块儿会先于构造方法执行)。
 * <p>
 * 使用匿名内部类进行初始化：在new 一个对象的时候，小括号后边跟一个大括号
 * 使用匿名内部类 + 实例化代码块儿 = 使用两个大括号进行初始化
 */
public class TestInstanceCodeBlock {
    public static void main(final String[] args) {

        //匿名内部类
        Person person = new Person("张三") {
            @Override
            public String getName() {
                return super.getName() + "123";
            }
        };
        System.out.println(person.getName());

        // 两个大括号的方式初始化(本质上是匿名内部类 + 实例化代码块儿)
        // 但有造成内存泄漏的风险--> 假如此种方式创建的对象被返回，其他对象持有，因为每个内部类都会持有一个外围实例的引用，则所有的这些匿名子类型都会持有一个这样的引用，heavyObject
        List<String> personList = new ArrayList<String>() {{
            add("AA");
            add("BB");
            add("CC");
        }};
        for (String s : personList) {
            System.out.println(s);
        }

    }
}


class Person {
    String name;

    public Person(String name) {
        this.name = name;
        System.out.println("构造方法执行...");
    }

    //实例化代码块儿,先于构造方法执行
    {
        System.out.println("实例代码块初始化...");
    }

    //静态代码块儿,类初始化的时候执行一次，先于实例代码块
    static {
        System.out.println("静态代码块初始化...");
    }

    public String getName() {
        return name;
    }

}
