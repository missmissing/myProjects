package com.feel.baseDataType;

import java.math.BigDecimal;

public class BigDecimalTest {
    private static BigDecimal b = new BigDecimal("-0.9");

    public static void main(String[] args) {
        // 未初始化使用会报错
        System.out.println(b.intValue());

        System.out.println(BigDecimal.ZERO.intValue());

        System.out.println(BigDecimal.ONE.byteValue());
    }
}
