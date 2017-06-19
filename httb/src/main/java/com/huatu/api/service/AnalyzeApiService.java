/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.service  
 * 文件名：				AnalyzeApiService.java    
 * 日期：				2015年6月12日-上午9:07:12  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.service;

import java.util.List;

import com.huatu.api.po.PaperPo;
import com.huatu.api.vo.QuesAttrVo;
import com.huatu.core.exception.HttbException;

/**   
 * 类名称：				AnalyzeApiService  
 * 类描述：  		 	分析服务
 * 创建人：				LiXin
 * 创建时间：			2015年6月12日 上午9:07:12  
 * @version 		1.0
 */
public interface AnalyzeApiService {
	/**
	 * 通过试题Id集合获取试题分析属性列表
	 * getQuesAttr  
	 * @exception 
	 * @param list - 试题id集合
	 * @return
	 * @throws HttbException
	 */
	public List<QuesAttrVo> getQuesAttr(List<String> list)throws HttbException;
	/** 1 -1
	 * 保存考试结果   【真题模拟题】
	 * SaveKaoshi   
	 * @exception 
	 * @param testpaper
	 */
	public void saveKaoshi(PaperPo testpaper)throws HttbException;
	/** 1 -2
	 * 保存答题记录
	 * SaveKaoshi   
	 * @exception 
	 * @param testpaper
	 */
	public void saveDati(PaperPo testpaper)throws HttbException;
	/**
	 * 错题保存
	 * saveMyErrorQues  
	 * @exception 
	 * @param testpaper
	 * @throws HttbException
	 */
	public void saveMyErrorQues(PaperPo testpaper)throws HttbException;
	/**
	 * 保存用户操作历史
	 * saveUserHistory  
	 * @exception 
	 * @param testpaper
	 * @throws HttbException
	 */
	public void saveUserHistory(PaperPo testpaper)throws HttbException;
}
