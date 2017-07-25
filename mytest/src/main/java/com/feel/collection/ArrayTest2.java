package com.feel.collection;

import java.util.Arrays;

/**
 * Created by lyan on 2017/3/14.
 */
public class ArrayTest2 {
    public static void main(String[] args) {
        int[] arr = new int[]{4, 2, 6, 1,3};

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                System.out.println(j);
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        System.out.println(Arrays.toString(arr));
    }
}
