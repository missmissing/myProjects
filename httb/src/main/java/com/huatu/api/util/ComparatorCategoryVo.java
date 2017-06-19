/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.util  
 * 文件名：				ComparatorCategoryVo.java    
 * 日期：				2015年6月24日-下午8:38:59  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.util;

import java.util.Comparator;

import com.huatu.api.vo.CategoryVo;


/**   
 * 类名称：				ComparatorTree  
 * 类描述：  			章节VO排序
 * 创建人：				LiXin
 * 创建时间：			2015年5月13日 下午3:49:15  
 * @version 		1.0
 */
@SuppressWarnings("rawtypes")
public class ComparatorCategoryVo implements Comparator {

	public int compare(Object arg0, Object arg1) {
		CategoryVo tree0=(CategoryVo)arg0;
		CategoryVo tree1=(CategoryVo)arg1;
		int flag=tree0.getOrdernum().compareTo(tree1.getOrdernum());
		if(flag==0){
			return tree0.getCid().compareTo(tree1.getCid());
		}else{
			return flag;
		}
	}
}