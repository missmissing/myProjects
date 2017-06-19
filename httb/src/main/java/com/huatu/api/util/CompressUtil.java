/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.util  
 * 文件名：				Compressutil.java    
 * 日期：				2015年6月16日-上午10:36:42  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**   
 * 类名称：				CompressUtil  
 * 类描述：  			压缩
 * 创建人：				LiXin
 * 创建时间：			2015年6月16日 上午10:36:42  
 * @version 		1.0
 */
public class CompressUtil {
//	public static void main(String args[]) throws IOException{
//		File file = new File("e:/category.txt");
//		StringBuilder sb = new StringBuilder();
//		String s ="";
//		BufferedReader br = new BufferedReader(new FileReader(file));
//
//		while( (s = br.readLine()) != null) {
//		sb.append(s );
//		}
//
//		br.close();
//		String str = sb.toString();
//		System.out.println( gunzip(str));
//	}
	/**
	 * 数据压缩
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String gzip(String primStr){
		if (primStr == null || primStr.length() == 0) {
			return primStr;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(primStr.getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return new sun.misc.BASE64Encoder().encode(out.toByteArray());
	}

	/**
	 * 数据解压缩
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String gunzip(String compressedStr) {
		if (compressedStr == null) {
			return null;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		byte[] compressed = null;
		String decompressed = null;
		try {
			compressed = new sun.misc.BASE64Decoder()
					.decodeBuffer(compressedStr);
			in = new ByteArrayInputStream(compressed);
			ginzip = new GZIPInputStream(in);

			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return decompressed;
	}
	
}
