package com.huatu.ou.statistics.dao;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.springframework.stereotype.Repository;

@Repository
public class StatisticsDao {

	private static long index = 0;
	private static long num = 0;

	public long queryHistoryNum() {
		RandomAccessFile randomFile = null;
		boolean contains;
		try {
			randomFile = new RandomAccessFile("d:/logs/httblog.log", "r");
			randomFile.seek(index);
			while (randomFile.getFilePointer() < randomFile.length()) {
				contains = new String(randomFile.readLine().getBytes(
						"ISO-8859-1"), "gbk").contains("用户登录");
				if (contains) {
					num++;
				}
			}
			index = randomFile.length();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e1) {
				}
			}
		}
		return num;
	}
}
