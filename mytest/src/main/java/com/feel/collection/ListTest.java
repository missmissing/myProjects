package com.feel.collection;

import java.util.List;

/**
 * Created by lyan on 2017/3/14.
 */
public class ListTest {
    public static void main(String[] args) {
        People p = new People();

        for(String s : p.getList()){
            System.out.println(s);
        }
    }

    public static class People{
        List<String> list;

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }
}
