package cn.itcast.erp.biz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;

import cn.itcast.erp.entity.ActivitiQuery;

public interface IActivitiBiz {
	/**
	 * 启动业务流程
	 */
	public void startBizProcess(String processKey,String businessKey,String userId);
	
	/**
	 * 完成业务流程
	 * @param businessKey
	 */
	public void completeBizProcess(String businessKey,String userId);
	
	/**
	 * 完成业务流程(方法重载，增加审批条件：如果金额小于1万由财务经理审批，如果金额大于等于1万由总经理审批)
	 * @param businessKey
	 */
	public void completeBizProcess(String businessKey,String userId,Map variables);
	
	
	/**
	 * 根据流程定义Key和任务节点定义key（如doCheck）查询业务Id列表
	 * @param pdkey    流程定义Key
	 * @param task_def_key   任务节点定义key
	 * @return
	 *//*
	public List<Long> getBusinessIdList(String pdkey,String task_def_key);
	
	*/

	/**
	 * 根据流程定义Key和用户ID查询业务Id列表
	 * @param pdkey
	 * @param ByUserId   登陆用户ID
	 * @return
	 */
	public List<Long> getBusinessIdListByUserId(String pdkey,String userId);
	
	
	/**
	 * 历史任务查询
	 * @return
	 */
	public List<HistoricTaskInstance> getHistorivTaskInstance(ActivitiQuery activitiQuery,int firstResult, int maxResults);

	/**
	 * 得到历史任务的所有记录数
	 * @return
	 */
	public Long getHistoricTaskInstanceQueryCount(ActivitiQuery activitiQuery);
	
	/**
	 * 部署流程图
	 * @param file
	 * @throws FileNotFoundException 
	 */
	public void deploy(File file) throws FileNotFoundException;
	
	/**
	 * 得到流程实例列表
	 * @return
	 */
	public List<ProcessInstance> getProcessInstanceList();
	
	
	/**
	 * 将图片信息写入到输出流中
	 * @param os   输出流
	 * @param processDefId  流程定义ID
	 * @throws IOException
	 */
	public void writePngToOs(OutputStream os,String processDefId,String activitiId) throws IOException;
}
