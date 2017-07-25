package com.feel.collection;


import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lyan on 2017/3/14.
 */
public class ArrayTest {
    public static void main(String[] args) {
        String[] arr = new String[]{"A", "1", "C", "3", "B", "2"};

        int length = arr.length;

        for (int i = 0; i < length-1; i++) {
            for (int j = 0; j < length-1-i; j++) {
                if(compareAWithB(arr[j],arr[j+1])){
                    swap(arr,j,j+1);
                }
            }
        }

        System.out.println(Arrays.toString(arr));
    }

    public static boolean compareAWithB(String a,String b){
        if(isNum(a) && !isNum(b)){
            return true;
        }
        return false;
    }

    public static void swap(String[] arr, int i, int j) {
        String tempt = arr[i];
        arr[i] = arr[j];
        arr[j] = tempt;
    }

    public static boolean isNum(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
}
