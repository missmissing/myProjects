package com.huatu.tb.category.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.tb.category.model.Category;

/**
 *
 * 类名称：				QuesInput
 * 类描述：  				试题导入工具类
 * 创建人：				Aijunbo
 * 创建时间：				2015年5月5日 下午2:09:32
 * @version 			0.0.1
 */
@Service
public class CateInputServiceImpl {
	//需要插入题目集合
	private List<Category> cateList = new ArrayList<Category>();

	//异常记录
	private List<String> errors = new ArrayList<String>();

	/**
	 *
	 * readTxtFile					(读取文件内容)
	 * @param 		filePath		文件路径
	 * @param		createuser		创建者
	 */
	@SuppressWarnings("finally")
	public List<Category> readTxtFile(File file, String createuser) {
		try {
			//编码类型
			String encoding = "GBK";
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineText = null;

				//循环遍历
				while ((lineText = bufferedReader.readLine()) != null) {
					if(lineText.trim().length() > 0){
						Category cate = splitCategory(lineText, createuser);
						if(cate != null){
							cateList.add(cate);
						}

					}
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		} finally{
			outputErrors();
			return cateList;
		}
	}

	/**
	 *
	 * splitCategory				(封装category对象)
	 * @param 		lineText		当前行数据
	 * @param 		createuser		创建者
	 * @return
	 */
	private Category splitCategory(String lineText, String createuser){
		//初始化知识点对象
		Category c = null;
		//名称#编号#排序
		String[] splits = lineText.split("#");
		if(splits.length == 3){
			c = new Category();
			c.setCreateuser(createuser);
			c.setCreatetime(new Date());
			c.setTombstone("0");
			//是指名称
			c.setCname(splits[0]);
			//设置编号
			c.setCid(splits[1]);
			//未给定排序字段或为非数字
			if(splits[2].trim().length() < 1 || !splits[2].matches("[0-9]*")){
				//默认为0
				c.setCordernum(0);
			}else{
				c.setCordernum(Integer.parseInt(splits[2]));
			}

		}else{
			/**异常输出到日志文件中*/
			errors.add(lineText);
		}
		return c;
	}

	/**
	 *
	 * outputErrors					(导入异常知识点记录)
	 * @param errors
	 */
	private void outputErrors(){
		File data = new File(Constants.CATEGORY_OUTPUT+CommonUtil.formatDate(new Date(), "yyyyMMddhhmmss")+".txt");
		if(!data.exists()){
    		try {
				data.createNewFile();
				OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(data),"GBK");
	            for (int i = 0; i < errors.size(); i++) {
	            	out.write(errors.get(i)+"\r\n");
				}
	            out.close();
	            errors = new ArrayList<String>();
			} catch (IOException e) {
				System.out.println("文件创建失败");
				e.printStackTrace();
			}
    	}
	}
}
