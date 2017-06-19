package com.huatu.tb.category.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.huatu.core.exception.HttbException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.tb.category.model.Category;
import com.huatu.tb.category.model.CategoryInputMessage;


/**
 *
 * 类名称：				QuesInput
 * 类描述：  				试题导入工具类
 * 创建人：				Aijunbo
 * 创建时间：				2015年5月5日 下午2:09:32
 * @version 			0.0.1
 */
public class CategoryInput {
	@Autowired
	private IRedisService iRedisService;
	//需要插入题目集合
	private List<Category> cateList = new ArrayList<Category>();

	//异常记录
	private List<String> errors = new ArrayList<String>();
	/**
	 *
	 * readTxtFile					(读取文件内容)
	 * @param 		filePath		文件路径
	 * @param		createuser		创建者
	 * @throws 		HttbException
	 */
	@SuppressWarnings("finally")
	public CategoryInputMessage readTxtFile(InputStream inputStream, String createuser) throws HttbException {
		String errorName = null ;
		try {
			//编码类型
			String encoding = "GBK";
			if (inputStream != null) { // 判断输入流是否为空
				InputStreamReader read = new InputStreamReader(inputStream, encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineText = null;
				//是否医疗知识树
				boolean isYL = true;

				//循环遍历
				while ((lineText = bufferedReader.readLine()) != null) {
					if(CommonUtil.isNotEmpty(lineText)){
						//是否医疗
						if(isYL){
							String[] splits = lineText.split("#");
							//如果判断是以1开头(1表示医疗),则先加上医疗根节点
							if(splits[1].startsWith("1")){
								isYL = false;
								String ylroot = "医疗#"+Constants.YL_ROOTID+"#0";
								Category cate = splitCategory(ylroot, createuser);
								if(cate != null){
									cateList.add(cate);
								}
							}
						}
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
			//如果异常集合不为空
			if(errors.size() > 0){
				errorName = "error_Category"+CommonUtil.formatDate(new Date(), "yyyyMMddhhmmss");
				//错题记录存入redis
				iRedisService.putEX(errorName, errors);
				//重置集合
				errors = new ArrayList<String>();
			}
			CategoryInputMessage categoryInputMessage = new CategoryInputMessage();
			categoryInputMessage.setCateList(cateList);
			categoryInputMessage.setErrorName(errorName);
			return categoryInputMessage;
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
		//名称#编号#排序#父节点ID
		String[] splits = lineText.split("#");

		//如果是金融的根节点，则改变节点id(200==>m200)
		if(splits[1].length() == Constants.CATEGORY_ID_LEN && splits[1].equals("200")){
			splits[1] = Constants.JR_ROOTID;
		}

		if(splits.length == 3){
			//封装分类对象(没父节点ID)
			c = setCategory(createuser, splits);
			if(CommonUtil.isNotEmpty(splits[1])){
				c.setCpid(getPid(splits[1]));
				c.setClevels(getLevels(splits[1]));
			}
		}else if(splits.length == 4){
			//封装分类对象(有父节点ID)
			c = setCategory(createuser, splits);
			//如果父节点ID不为空
			if(CommonUtil.isNotEmpty(splits[3])){
				c.setCpid(splits[3]);
				c.setClevels(getLevels(splits[1]));
			}else{
				if(CommonUtil.isNotEmpty(splits[1])){
					//按3位长度截取父节点ID
					c.setCpid(getPid(splits[1]));
					c.setClevels(getLevels(splits[1]));
				}
			}

		}else{
			/**异常输出到日志文件中*/
			errors.add(lineText);
		}
		return c;
	}

	/**
	 *
	 * setCategory					(封装知识分类对象)
	 * @param 		createuser		创建者名称
	 * @param 		splits			知识分类数组
	 * @return
	 */
	private Category setCategory(String createuser, String[] splits) {
		//知识分类对象
		Category c = new Category();
		//设置创建者
		c.setCreateuser(createuser);
		//设置创建时间
		c.setCreatetime(new Date());
		//设置是否有效
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
		return c;
	}

	/**
	 *
	 * outputErrors					(导入异常知识点记录)
	 * @param 		path			路径
	 */
	public String outputErrors(List<String> errors){
		StringBuffer catelogs = new StringBuffer("");
        for (int i = 0; i < errors.size(); i++) {
        	catelogs.append(errors.get(i)+"\r\n");
		}
        errors = new ArrayList<String>();
        return catelogs.toString();
	}

	/**
	 *
	 * getPid						(获取父节点ID)
	 * 								(导入知识分类时没有传父节点ID，则截取当前节点ID)
	 * @param 		cid				当前节点ID
	 * @return		String			父节点ID
	 */
	private String getPid(String cid){
		//父节点ID
		String cpid = null;

		//判断是否大于节点基本长度
		if(cid.length() >= Constants.CATEGORY_ID_LEN && !cid.equals(Constants.YL_ROOTID) && !cid.equals(Constants.JR_ROOTID)){
			//医疗2级节点
			if(cid.length() == Constants.CATEGORY_ID_LEN && cid.startsWith("10")){
				cpid = Constants.YL_ROOTID;
			}else if(cid.length() == Constants.CATEGORY_ID_LEN*2 && cid.startsWith("200")){
				//金融2级节点
				cpid = Constants.JR_ROOTID;
			}else{
				cpid = cid.substring(0,cid.length()-Constants.CATEGORY_ID_LEN);
			}
		}else if(cid.equals(Constants.YL_ROOTID) || cid.equals(Constants.JR_ROOTID)){
			//默认根节点为0
			cpid = "0";
		}else{
			//默认根节点为0
			cpid = "0";
		}
		return cpid;
	}

	/**
	 *
	 * getLevels					(根据当前节点ID获取节点层级)
	 * 								(上传知识节点时调用)
	 * @param 		cid				当前节点ID
	 * @return
	 */
	private String getLevels(String cid){
		String levels = null;
		//医疗根节点是手动添加，未按照标准模板添加
		if(cid.equals(Constants.YL_ROOTID)){
			levels = "1";
		}else if(cid.startsWith("1")){
			//以1开头表示医疗
			//由于医疗根节点由程序添加，所以层级默认从2级开始
			levels = cid.length()/Constants.CATEGORY_ID_LEN+1+"";
		}else{
			levels = cid.length()/Constants.CATEGORY_ID_LEN+"";
		}
		return levels;
	}
}
