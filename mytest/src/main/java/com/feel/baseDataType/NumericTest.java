package com.feel.baseDataType;

public class NumericTest {

    public static void main(String[] args) {
        Byte b1 = 5;
        byte b2 = 32;
        //System.out.println(b1 + b2);

        System.out.println((int) b1);
        System.out.println((int) b2);

        //testByte(Byte.parseByte("-4"));
        String row = "1";
        System.out.println(str2Int(row));
    }

    private static void testByte(Byte aByte) {
        System.out.println(aByte);

        //System.out.println(Byte.(aByte));
    }

    public static Integer str2Int(String str) {
        return Double.valueOf(str).intValue();
    }
}
