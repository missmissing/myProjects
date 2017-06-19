/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.paper.util  
 * 文件名：				ComparatorToPaper.java    
 * 日期：				2015年6月23日-下午1:23:38  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.tb.paper.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.huatu.core.util.CommonUtil;
import com.huatu.tb.paper.model.Paper;


/**   
 * 类名称：				ComparatorToPaper  
 * 类描述：  			试卷按修改时间排序
 * 创建人：				LiXin
 * 创建时间：			2015年6月23日 下午1:23:38  
 * @version 		1.0
 */
public class ComparatorToPaper implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
			Paper p1=(Paper)o1;
			Paper p2=(Paper)o2;
			if(CommonUtil.isNull(p1.getPyear())){
				p1.setPyear("1970");
			}
			if(CommonUtil.isNull(p2.getPyear())){
				p2.setPyear("1970");
			}
			
			if(CommonUtil.isNotNull(p1.getPyear())){
				int flag=p2.getPyear().compareTo(p1.getPyear());
				return flag;
			}else{
				int flag=p1.getCreatetime().compareTo(p2.getCreatetime());
				return flag;
			}
			
	}
//	public static void main(String args[]){
//		List<Paper> paperList = new ArrayList<Paper>();
//		Paper p1 = new Paper();
//		p1.setPyear("2000");
//		paperList.add(p1);
//		
//		Paper p2 = new Paper();
//		p2.setPyear("2015");
//		paperList.add(p2);
//		
//		Paper p3 = new Paper();
//		p3.setPyear("2008");
//		paperList.add(p3);
//		ComparatorToPaper comparator = new ComparatorToPaper();
//		Collections.sort(paperList, comparator);//根据排序字段排序
//		for(Paper p : paperList){
//			System.out.println(p.getPyear());
//		}
//		
//	}

}
