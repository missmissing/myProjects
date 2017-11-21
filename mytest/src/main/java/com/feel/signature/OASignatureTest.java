package com.feel.signature;

import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class OASignatureTest {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        /**
         * l  生成方法
         依次按时间戳、AppId、UserId、AppKey的顺序拼接，再对拼接后的字符串进行SHA1(UTF-8编码)方式加密生成加密字符串Security，然后依次按时间戳、AppId、UserId、Security的顺序拼接，再对拼接后的字符串转成Base64(UTF-8编码)格式，即为最终Token。
         注：其中时间戳与传输数据中的时间格式有所区别，token中时间戳格式为生成时刻的时间(格式：yyyyMMddhhmmss,如20151026103600=2015/10/26 10:36:00)
         l  示例(示例仅供参考，实际使用请替换为分配的appid及appkey)

         AppId=0001

         UserId=12345678

         AppKey=B5E97EE9D836449699EA0549CCA4847D

         Timestamp=20151026103600

         生成后的token:

         MjAxNTEwMjYxMDM2MDAwMDAxMTIzNDU2NzgyRDFDMDdGQ0E5NTkxN0M1MzhDMDI4RDgyM0E2NEE5NTRENzc5MDg4

         */

        String appId = "0001";
        String userId = "12345678";
        String appKey = "B5E97EE9D836449699EA0549CCA4847D";
        String timestamp = "20151026103600";

        String a = new String(Base64.getDecoder().decode("MjAxNTEwMjYxMDM2MDAwMDAxMTIzNDU2NzgyRDFDMDdGQ0E5NTkxN0M1MzhDMDI4RDgyM0E2NEE5NTRENzc5MDg4".getBytes()));
        System.out.println(a);

        // 201510261036000001123456782D1C07FCA95917C538C028D823A64A954D779088
        System.out.println(DigestUtils.sha1Hex("20151026103600000112345678" + appKey));

        /*MessageDigest md = MessageDigest.getInstance("MD5");
        return md.digest(s.getBytes("UTF-8"));*/

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] md5 = md.digest("apple".getBytes(StandardCharsets.UTF_8));

        String token0 = new String(md5, StandardCharsets.UTF_8);
        String token1 = byteArrayToHexString(md5);
        String token2 = new String(Base64.getEncoder().encode(md5));

        System.out.println("===");
        System.out.println(token0);
        System.out.println(token1);
        System.out.println(token2);
        System.out.println("===");

        System.out.println(new String(Base64.getDecoder().decode(token2.getBytes())));
        System.out.println(new String(Base64.getEncoder().encode(token1.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8));
    }

    public static String byteArrayToHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        String K_HEX_CHARS = "0123456789ABCDEF";
        StringBuffer b = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; ++i) {
            int hex = bytes[i] & 0xFF;
            b.append(K_HEX_CHARS.charAt(hex >> 4));
            b.append(K_HEX_CHARS.charAt(hex & 0x0f));
        }
        return b.toString();
    }

    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }
}