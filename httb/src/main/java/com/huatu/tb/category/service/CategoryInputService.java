package com.huatu.tb.category.service;

import java.io.File;
import java.util.List;

import com.huatu.tb.category.model.Category;

public interface CategoryInputService {
	/**
	 *
	 * readTxtFile					(读取文件内容)
	 * @param 		filePath		文件路径
	 * @param		createuser		创建者
	 */
	public List<Category> readTxtFile(File file, String createuser);
}
