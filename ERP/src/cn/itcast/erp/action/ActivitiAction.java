package cn.itcast.erp.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import util.FileUtil;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;

import cn.itcast.erp.biz.IActivitiBiz;
import cn.itcast.erp.entity.ActivitiQuery;

public class ActivitiAction {
	
	private IActivitiBiz activitiBiz;  //注入业务逻辑层
	
	public IActivitiBiz getActivitiBiz() {
		return activitiBiz;
	}
	public void setActivitiBiz(IActivitiBiz activitiBiz) {
		this.activitiBiz = activitiBiz;
	}

	
	private int page;  //存储分页所需的当前页面
	private int rows;   //存储每页的记录数

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
	private ActivitiQuery activitiQuery;  //存储前端的查询条件
	
	public ActivitiQuery getActivitiQuery() {
		return activitiQuery;
	}
	public void setActivitiQuery(ActivitiQuery activitiQuery) {
		this.activitiQuery = activitiQuery;
	}
	/**
	 * 查询历史任务实例信息
	 */
	public void historicTaskInstanceQuery(){
		int firstResult = (page-1)*rows;
		//调用方法得到分页查询的所有数据
		List<HistoricTaskInstance> list = activitiBiz.getHistorivTaskInstance(activitiQuery,firstResult, rows);
		//调用方法得得到所有的记录数
		Long count = activitiBiz.getHistoricTaskInstanceQueryCount(activitiQuery);
		//构建map集合（因为在前端页面中datagrid需要的数据格式为total:'',rows:'{}'的json数据）
		Map map = new HashMap();
		map.put("total", count);
		map.put("rows", list);
		
		String jsonString = JSON.toJSONString(map);
		write(jsonString);
	}
	
	//文件上传，封装上传的文件
	private File file;   //上传的文件
	private String fileFileName;  //上传文件的名称
	private String fileContentType;  //上传文件的类型
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	
	/**
	 * 文件上传
	 * @throws IOException 
	 */
	public String upload() throws IOException{
		/*//得到上传文件保存的目录
		String filename = 
				ServletActionContext.getServletContext().getRealPath("/upload")+"/"+fileFileName;
		File newFile = new File(filename);
		//如果该文件不存在，直接创建
		if(!newFile.getParentFile().exists()){  //判断父级目录（即upload目录）是否存在
			newFile.getParentFile().mkdirs();  //创建目录
		}
		FileUtils.copyFile(file, newFile);  //拷贝文件
		*/
		
		//调用方法实现流程图的部署
		activitiBiz.deploy(file);
		return  "success";
	}
	
	/**
	 * 输出信息
	 * @param jsonString
	 */
	public void write(String jsonString)
	{		
		//得到响应对象 		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		//输出
		try {
			response.getWriter().print(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 得到历程实例列表
	 * @return
	 */
	public String getProcessInstanceList(){
		//调用方法得到流程实例列表
		List<ProcessInstance> plist = activitiBiz.getProcessInstanceList();
		//把得到的列表写入值栈的栈顶
		ActionContext.getContext().put("list", plist);
		
		return "processInstanceList";
	}
	
	private String processDefinitionId;  //用来存储流程定义id

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	
	private String activityId;  //任务节点ID
	
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	//
	public void getProcessImage(){
		//定义输出流，将得到的图片信息，保存到该输出流中
	    try {
	    	ServletOutputStream os = ServletActionContext.getResponse().getOutputStream();
			activitiBiz.writePngToOs(os, processDefinitionId,activityId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
