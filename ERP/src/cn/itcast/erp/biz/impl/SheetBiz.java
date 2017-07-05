package cn.itcast.erp.biz.impl;

import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import antlr.CharFormatter;

import cn.itcast.erp.biz.ISheetBiz;
import cn.itcast.erp.dao.impl.ISheetDao;
import cn.itcast.erp.entity.Sheet;

public class SheetBiz implements ISheetBiz{
	
	private ISheetDao sheetDao;  //注入数据层
	public void setSheetDao(ISheetDao sheetDao) {
		this.sheetDao = sheetDao;
	}


	/**
	 * 得到销售统计报表
	 * @param date1   //按日期进行查询：条件一
	 * @param date2	  //按日期进行查询：条件二
	 * @return
	 */
	public List getOrderSheet(Date date1,Date date2){
		return sheetDao.getOrderSheet(date1, date2);
	}
	
	/**
	 * 获得销售统计图
	 * @param os
	 * @param date1
	 * @param date2
	 * @throws IOException 
	 */
	public void getOrderChart(OutputStream os,Date date1,Date date2) throws IOException{
		
		//获得图表所需的数据
		List<Sheet> sheetList = sheetDao.getOrderSheet(date1, date2);
		//设置图表所需的数据集
		DefaultPieDataset dataset = new DefaultPieDataset();
		for(Sheet sheet:sheetList){
			dataset.setValue(sheet.getName(), sheet.getMoney());
		}
		
		//创建图表对象
		JFreeChart chart = ChartFactory.createPieChart("销售统计分析图", dataset, true, false, false);
		  //解决标题的中文乱码问题
		    chart.setTitle(new TextTitle("销售统计图", new Font("黑体",Font.BOLD,25)));
		  //设置图表的子图
		  PiePlot plot = (PiePlot) chart.getPlot();
		  plot.setLabelFont(new Font("黑体",Font.BOLD,14));
		  
		  //设置图例的字体
		  LegendTitle legend = chart.getLegend();
		  legend.setItemFont(new Font("宋体",Font.BOLD,16));
				
	   //将图表对象写入到输出流中
		ChartUtilities.writeChartAsPNG(os, chart, 480, 380);
	}
	
	/**
	 * 导出销售报表
	 * @param os   输出流
	 * @param date1    销售结束时间：查询条件一
	 * @param date2    销售结束时间：查询条件二
	 * @throws IOException
	 */
	public void exportExcel(OutputStream os,Date date1,Date date2) throws IOException{
		//得到查询的报表数据
		List<Sheet> sheetList = sheetDao.getOrderSheet(date1, date2);
		
		//创建一个工作簿
		HSSFWorkbook book = new HSSFWorkbook();
		//创建一个工作表 
		HSSFSheet st = book.createSheet();
		String[] title = {"商品分类","金额"};   //工作表的标题内容
		//创建第一行，并写入标题内容
		HSSFRow row0 = st.createRow(0);
		
		for(int i=0;i<title.length;i++){
			row0.createCell(i).setCellValue(title[i]);
		}
		//把得到的销售报表中的数据写入到工作表中
		for(int i=0;i<sheetList.size();i++){
			//每次循环都创建一行
			HSSFRow row1 = st.createRow(i+1);
			//得到每一行的实体
			Sheet s = sheetList.get(i);
			
			//创建单元格把实体中的数据写入到没有对应的单元格中
			row1.createCell(0).setCellValue(s.getName());
			row1.createCell(1).setCellValue(s.getMoney());
		}
		
		//把工作簿写入到输出流中
		book.write(os);
	}
}
