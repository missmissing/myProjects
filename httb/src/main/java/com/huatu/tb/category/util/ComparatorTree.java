/**
 * 项目名：				httb
 * 包名：				com.huatu.tb.category.util
 * 文件名：				ComparatorTree.java
 * 日期：				2015年5月13日-下午3:49:15
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.tb.category.util;

import java.util.Comparator;

import com.huatu.core.model.Tree;

/**
 * 类名称：				ComparatorTree
 * 类描述：  			树排序
 * 创建人：				LiXin
 * 创建时间：			2015年5月13日 下午3:49:15
 * @version 		1.0
 */
@SuppressWarnings("rawtypes")
public class ComparatorTree implements Comparator {

	@Override
	public int compare(Object arg0, Object arg1) {
		Tree tree0=(Tree)arg0;
		Tree tree1=(Tree)arg1;
		int flag=tree0.getOrderNum().compareTo(tree1.getOrderNum());
		if(flag==0){
			return tree0.getId().compareTo(tree1.getId());
		}else{
			return flag;
		}
	}

}
