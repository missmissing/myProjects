package com.feel.circulation;

public class LoopDemo {
    public static void main(String[] args) {
        zengzs: //跳到此处的话 ，全部结束，继续往下执行
        for (int i = 0; i < 2; i++) {
            System.out.println("运行第一重循环" + i);
            //for (int j = 0; j < 2; j++) {
            label: //跳到此处，继续执行第一重循环，且第二重循环 j 从0 重新开始
            for (int j = 0; j <2; i++) {
                System.out.println("运行第二重循环" + j);
                for (int k = 0; k < 2; k++) {
                    // break label;
                    if (k == 1) {
                        System.out.println("跳出多重循环");
                        //break zengzs;
                        break label;
                    }
                    System.out.println("运行第三重循环" + k);
                    System.out.println("**************************");
                }
            }
        }

        System.out.println("*************END*************");
    }
}
