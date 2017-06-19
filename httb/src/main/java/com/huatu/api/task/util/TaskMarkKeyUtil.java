/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.task.util  
 * 文件名：				TaskMarkKeyUtil.java    
 * 日期：				2015年6月21日-下午3:21:58  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.task.util;

/**   
 * 类名称：				TaskMarkKeyUtil  
 * 类描述：  			定时任务标记常量【目前包含章节  试卷相关  待续...】
 * 创建人：				LiXin
 * 创建时间：			2015年6月21日 下午3:21:58  
 * @version 		1.0
 */
public class TaskMarkKeyUtil {
	
	/**章节-试题关系 redis中的key*/
	public static final String CATE_QUES_KEY = "cate_ques_key_gx";
	/**真题列表在redis中的key标记*/
	public static final String ZHEN_PAPER_LIST = "zhen_paper_list";
	
	/**模拟题列表在redis中的key标记*/
	public static final String MONI_PAPER_LIST = "moni_paper_list";
	
	/**真题版本Map集合在redis中的key标记*/
	public static final String ZHEN_VER_GATHER = "zhen_ver_gather";
	
	/**模拟题题版本Map集合在redis中的key标记*/
	public static final String MONI_VER_GATHER = "moni_ver_gather";
	
	/**章节版本在redis中的key标记*/
	public static final String CATE_VER_GATHER = "cate_ver_gather";
	
	/**章节试题打包版本在redis中的key标记*/
	public static final String QUESCATE_VER_GATHER = "quescate_ver_gather";
	
	/**章节(知识点）树 在redis中的key*/
	public static final String CATEGORY_KEY = "category_key";
	
	/**章节(知识点）试题打包在redis中key前缀  后跟章节ID*/
	public static final String CATEQUES_ = "cateques_";
	
	/**试卷打包放入redis的key前缀  后跟试卷ID*/
	public static final String PAPER_ = "paper_";
	
	
	
}
