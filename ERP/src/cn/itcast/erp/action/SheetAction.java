package cn.itcast.erp.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.ISheetBiz;

public class SheetAction{
	
	private ISheetBiz sheetBiz; //注入业务层
	public void setSheetBiz(ISheetBiz sheetBiz) {
		this.sheetBiz = sheetBiz;
	}

	//属性驱动
	private Date date1;
	private Date date2;

	public Date getDate1() {
		return date1;
	}
	public void setDate1(Date date1) {
		this.date1 = date1;
	}
	public Date getDate2() {
		return date2;
	}
	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	/**
	 * 得到销售统计报表
	 */
	public void getOrderSheet(){
		List orderSheet = sheetBiz.getOrderSheet(date1, date2);
		String jsonString = JSON.toJSONString(orderSheet);
		write(jsonString);
	}

	/**
	 * 得到销售统计图
	 * @throws IOException
	 */
	public void getOrderChart() throws IOException{
		//创建输出流
		ServletOutputStream os = ServletActionContext.getResponse().getOutputStream();
		sheetBiz.getOrderChart(os, date1, date2);
	}
	
	/**
	 * 导出Excel报表
	 * @throws IOException 
	 */
	public void exportExcel() throws IOException{
		//设置下载的文件名，并response响应的处理中文乱码
		String filename = new String("销售统计表.xls".getBytes(),"ISO-8859-1");
		//设置下载的头文件
		ServletActionContext.getResponse().setHeader("Content-Disposition", "attachment;filename="+filename);
		//创建输出流
		ServletOutputStream os =ServletActionContext.getResponse().getOutputStream();
		
		sheetBiz.exportExcel(os, date1, date2);
	}
	
	/**
	 * 向浏览器输出内容
	 * @param jsonString
	 */
	public void write(String jsonString){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try{
			response.getWriter().write(jsonString);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
