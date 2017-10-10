package com.feel.id;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomNumTest {
    public static void main(String[] args) {
        int i = 0;
        Set<String> s1 = new HashSet<>();
        while (i < 200000) {
            //System.out.println(genRandomNum(15));
            //System.out.println(genContractNo());
            s1.add(genContractNo());
            i++;
        }
        System.out.println(s1.size());

        int j=0;
        Set<String> s2 = new HashSet<>();
        while (j < 200000) {
            //System.out.println(genRandomNum(15));
            //System.out.println(genContractNo());
            s2.add(genRandomNum(10));
            j++;
        }
        System.out.println(s2.size());
    }
    private static String genContractNo() {
        return "NO-" + (new Random().nextInt(99999999) % (99999999 - 10000000 + 1) + 100000000);
    }


    /**
     * 生成随机密码
     *
     * @param pwd_len 生成的密码的总长度
     * @return 密码的字符串
     */
    public static String genRandomNum(int pwd_len) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 10;
        int i; // 生成的随机数  
        int count = 0; // 生成的密码的长度  
        /*char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};*/
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < pwd_len) {
        // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1  
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }
}
