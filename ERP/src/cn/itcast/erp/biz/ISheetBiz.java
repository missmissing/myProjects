package cn.itcast.erp.biz;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

public interface ISheetBiz {
	/**
	 * 得到销售统计报表
	 * @param date1   //按日期进行查询：条件一
	 * @param date2	  //按日期进行查询：条件二
	 * @return
	 */
	public List getOrderSheet(Date date1,Date date2);
	
	/**
	 * 获得销售统计图
	 * @param os
	 * @param date1
	 * @param date2
	 * @throws IOException 
	 */
	public void getOrderChart(OutputStream os,Date date1,Date date2) throws IOException;

	/**
	 * 导出销售报表
	 * @param os   输出流
	 * @param date1    销售结束时间：查询条件一
	 * @param date2    销售结束时间：查询条件二
	 * @throws IOException
	 */
	public void exportExcel(OutputStream os,Date date1,Date date2) throws IOException;
}
