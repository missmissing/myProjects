package cn.itcast.erp.dao.impl;

import java.util.Date;
import java.util.List;

public interface ISheetDao {
	/**
	 * 得到销售统计报表
	 * @param date1   //按日期进行查询：条件一
	 * @param date2	  //按日期进行查询：条件二
	 * @return
	 */
	public List getOrderSheet(Date date1,Date date2); 
}
