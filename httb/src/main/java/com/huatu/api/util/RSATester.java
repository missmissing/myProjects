/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.util  
 * 文件名：				RSATester.java    
 * 日期：				2015年6月12日-下午3:32:00  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.util;

import java.util.Map;

/**   
 * 类名称：				RSATester  
 * 类描述：  
 * 创建人：				LiXin
 * 创建时间：			2015年6月12日 下午3:32:00  
 * @version 		1.0
 */
public class RSATester {

	 static String publicKey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNsq9p0RVZPbdEffcRd2QdCwnOLQEemDsIqGtFkCC2jtjiG2n+k1tU8pcvn5z+ZvkHsp87eVkhrZY7k1jVEstnfce4WEYVLR2LXhOaITFr65AJM/zBtCa4xHnVDXNKW3OlrSXndq45ZGpM3ZrUSndCdRW8ZWqd+s9i+8g/t8xdMQIDAQAB";  
	 static String privateKey ="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI2yr2nRFVk9t0R99xF3ZB0LCc4tAR6YOwioa0WQILaO2OIbaf6TW1Tyly+fnP5m+Qeynzt5WSGtljuTWNUSy2d9x7hYRhUtHYteE5ohMWvrkAkz/MG0JrjEedUNc0pbc6WtJed2rjlkakzdmtRKd0J1Fbxlap36z2L7yD+3zF0xAgMBAAECgYAVBayGm3F6YvCcpb+RBNCCio+y2fRvkJA5kZsQn/hqfTjqGr0X+s6zBKavdxO4nphVrwd2sGMr3vAJ+KtWOUqZ32LlaQT7g1RVEbjU3EGOayIdvMd7/nZ0cvB+I8gcrkZQa+8szfuhGEX7+yoL59O2brDof2Fz80jMpyoaw8VSvQJBAOjrCuF9YZXF4j48sb/2pVehXDKCm7UY2TfzdeJOdbRu6DL7FOQcx1LLDkqd8G7ByVFCCi2VgYQ9IpS49vZy1TsCQQCbvXTU9CGCBQ4YjIMWrijLXihVxx1wz7ZHzpCOG/INVZsBbn7rjMHFL5n1lybX5rPDxMMxYhHVQhSXbtyLh8CDAkA7YyKlF9j33Tqwb71KmtLRoxzk7BRTeoB4GaVnDGZKmTSvOwG6n/PakleUguy48MQp6dK6iLI7S3MzxG2DrQfdAkAR+iubTVb02DqJUObs3kutNgA37pUSaZej8E81aDpnmaMb5jJWZFgVt+nwtHLyZxoHFhZmA+kFEeyokk54+MGbAkAzkc3rCx5/GOwKnFkaDy/kTwkeDzKoWbgAuPKtDeUpP7pUaABe3fZv1MZAeEvCrfMo1rWdSFyLT1hBpHCB56dg";  
	  
	    static {  
	        try {  
	            Map<String, Object> keyMap = RSAUtils.genKeyPair();  
	         //   publicKey = RSAUtils.getPublicKey(keyMap);  
	         //   privateKey = RSAUtils.getPrivateKey(keyMap);  
	            System.err.println("公钥: \n\r" + publicKey);  
	            System.err.println("私钥： \n\r" + privateKey);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	      
	    public static void main(String[] args) throws Exception {  
	        //test();  
	        testSign();  
	    }  
	    static void test() throws Exception {  
	        System.err.println("公钥加密——私钥解密");  
	        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";  
	        System.out.println("\r加密前文字：\r\n" + source);  
	        byte[] data = source.getBytes();  
	        byte[] encodedData = RSAUtils.encryptByPublicKey(data,privateKey );  
	        System.out.println("加密后文字：\r\n" + new String(encodedData));  
	        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, publicKey);  
	        String target = new String(decodedData);  
	        System.out.println("解密后文字: \r\n" + target);  
	    }  
	    
	    
	    
	    static void test1() throws Exception {  
	        System.err.println("公钥加密——私钥解密");  
	        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";  
	        System.out.println("\r加密前文字：\r\n" + source);  
	        byte[] data = source.getBytes();  
	        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);  
	        System.out.println("加密后文字：\r\n" + new String(encodedData));  
	        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);  
	        String target = new String(decodedData);  
	        System.out.println("解密后文字: \r\n" + target);  
	    }  
	  
	    static void testSign() throws Exception {  
	        System.err.println("私钥加密——公钥解密");  
	        String source = "这是一行测试RSA数字签名的无意义文字";  
	        System.out.println("原文字：\r\n" + source);  
	        byte[] data = source.getBytes();  
	        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);  
	        System.out.println("加密后：\r\n" + new String(encodedData));  
	        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);  
	        String target = new String(decodedData);  
	        System.out.println("解密后: \r\n" + target);  
	        System.err.println("私钥签名——公钥验证签名");  
	        String sign = RSAUtils.sign(encodedData, privateKey);  
	        System.err.println("签名:\r" + sign);  
	        boolean status = RSAUtils.verify(encodedData, publicKey, sign);  
	        System.err.println("验证结果:\r" + status);  
	    }  
	      
}
