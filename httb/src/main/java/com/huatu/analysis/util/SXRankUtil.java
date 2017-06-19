/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.util
 * 文件名：				SXRankUtil.java
 * 日期：				2015年6月23日-上午10:44:41
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.util;

import java.util.List;

import com.huatu.analysis.model.Htsxrank;
import com.huatu.core.util.CommonUtil;

/**
 * 类名称：				SXRankUtil
 * 类描述：  				顺序排名处理类
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月23日 上午10:44:41
 * @version 			0.0.1
 */
public class SXRankUtil {
	/**
	 *
	 * getSXRank					(获得好顺序的数组)
	 * 								(程序初始化，或者定时更新时调用)
	 * @param 		htsxranks		对顺序做题集合排序
	 * @return
	 */
	public static int[] getRankArrs(List<Htsxrank> htsxranks){
		int[] rankArrs = null;
		//判断是否为空
		if(CommonUtil.isNotNull(htsxranks)){
			//获得已经排好序的数组
			int[] arrs = toArrs(htsxranks);
			//初始化答完所有题数的数组集合(默认长度10000)
			rankArrs = new int[10000];
			//遍历排序集合(倒序由大到小)
			for(int i=arrs.length-1; i>=0; i--){
				//第一个默认是1
				if(i == arrs.length-1){
					rankArrs[arrs[i]] = 1;
					//其他的从2开始
					for (int j = 0; j < arrs[i]; j++) {
						rankArrs[j] = 2;
					}
				}else{
					for (int j = 0; j < arrs[i]; j++) {
						int rank = rankArrs[j];
						rankArrs[j] = rank+1;
					}
				}
			}
		}
		return rankArrs;
	}

	/***
	 *
	 * getArrs						(把集合答题数转成数组)
	 * @param 		htsxranks		顺序排名对象
	 * @return
	 */
	private static int[] toArrs(List<Htsxrank> htsxranks){
		//初始化数组
		ArrayIntIns arrs = new ArrayIntIns(htsxranks.size());
		//遍历集合
		for (int i = 0; i < htsxranks.size(); i++) {
			arrs.insert(htsxranks.get(i).getSxcount());
		}
		arrs.quickSort();
		return arrs.getTheArray();
	}

	public static void main(String[] args) {
		//上个节点答题数
		int prev = 0;
		//当前节点答题数
		int current = 0;
		int[] arrs = {1,1,1,3,4,6,7,9,10};
		//最大答题数+1(因为数组从0开始算的)
		int[] rankArrs = new int[arrs[arrs.length-1]+1];
		for (int i = arrs.length-1; i >= 0; i--) {
			if(i == arrs.length-1){
				//上个节点答题数
				prev = arrs[i];
				//当前节点答题数
				current = arrs[i];
			}else{
				//上个节点答题数
				prev = arrs[i+1];
				//当前节点答题数
				current = arrs[i];
			}

			//如果两个节点数目不一样，说明在统计另一个答题数的用户量
			//由于可能存在答题数不连续的情况，所以需要把两个数之间的用户量都补齐(按上一个答题数的排名算)
			if(prev != current){
				for (int j = current; j < prev; j++) {
					rankArrs[j] = rankArrs[prev];
				}
			}

			//当前答题数用户数量
			int count = rankArrs[arrs[i]];
			//如果答题数一样，只需要数字累加1
			count = count + 1;
			//重置当前答题数用户量
			rankArrs[arrs[i]] = count;
		}
		for(int i = rankArrs.length-1;i>=0;i--){
			System.out.println("答对"+i+"道题排名为:"+rankArrs[i]);
		}
	}


}
