package cn.itcast.erp.biz.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import cn.itcast.erp.biz.IActivitiBiz;
import cn.itcast.erp.entity.ActivitiQuery;

/**
 * 工作流业务逻辑父类
 * @author Administrator
 *
 * @param <T>
 */
public class ActivitiBiz<T> extends BaseBiz<T> implements IActivitiBiz{
	private ProcessEngine processEngine;   //注入流程引擎对象
	
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}
	
	/**
	 * 启动业务流程
	 */
	public void startBizProcess(String processKey,String businessKey,String userId){
		//获得运行时服务对象
		RuntimeService runtimeService = processEngine.getRuntimeService();
		//启动工作流，并得到流程实例（相当于把工作流和订单号绑定）
		ProcessInstance processInstance = runtimeService.
			startProcessInstanceByKey(processKey, businessKey);
		
		//获得任务ID(通过流程实例ID进行获得)
		TaskService taskService = processEngine.getTaskService();  //获得任务服务
		TaskQuery query = taskService.createTaskQuery();
		query.processInstanceId(processInstance.getId()); //用流程实例ID作为条件
		Task task = query.singleResult(); //得到任务对象
		String taskId = task.getId();  //获得任务ID
		
		//抓取任务
		taskService.claim(taskId, userId);
		//完成任务
		taskService.complete(taskId);
	}
	
	/**
	 * 完成业务流程
	 * @param businessKey
	 */
	public void completeBizProcess(String businessKey,String userId){
		completeBizProcess(businessKey, userId,null);
	}
	/**
	 * 完成业务流程(方法重载，增加审批条件：如果金额小于1万由财务经理审批，如果金额大于等于1万由总经理审批)
	 * @param businessKey
	 */
	public void completeBizProcess(String businessKey,String userId,Map variables){
		//根据业务ID(订单id)--->任务ID
		TaskService taskService = processEngine.getTaskService();
		TaskQuery tquery = taskService.createTaskQuery();
		tquery.processInstanceBusinessKey(businessKey);
		Task task = tquery.singleResult();
		String taskId = task.getId();  //得到任务ID
		
		//设置添加审批的条件
		if(variables!=null){
			taskService.setVariables(taskId, variables);
		}
		
		//抓取任务
		taskService.claim(taskId, userId);
		
		//完成任务
		taskService.complete(taskId);
	}
	
/*	*//**
	 * 根据流程定义Key和任务节点定义key（如doCheck）查询业务Id列表
	 * @param pdkey    流程定义Key
	 * @param task_def_key   任务节点定义key
	 * @return
	 *//*
	public List<Long> getBusinessIdList(String pdkey,String task_def_key){
		List<Long> ids = new ArrayList<Long>();
		//获得任务服务对象
		TaskService taskService = processEngine.getTaskService();
		//获得运行时服务对象
		RuntimeService runtimeService = processEngine.getRuntimeService();
		//通过任务对象，以流程实例key和任务节点key为条件进行查询，得到任务列表
		TaskQuery query = taskService.createTaskQuery();
		query.processDefinitionKey(pdkey);
		query.taskDefinitionKey(task_def_key);
		List<Task> taskList = query.list();
		//循环遍历任务列表，得到每一个任务
		for(Task task:taskList){
			//通过每一个任务得到流程实例ID，以流程实例ID为条件查询流程实例对象
			ProcessInstanceQuery pquery = runtimeService.createProcessInstanceQuery();
			pquery.processInstanceId(task.getProcessInstanceId());
			ProcessInstance p = pquery.singleResult();
			if(p.getBusinessKey()!=null){
				//通过流程实例对象，得到业务id
				ids.add(Long.valueOf(p.getBusinessKey()));
			}
		}
		
		return ids;
	}
	*/
	
	/**
	 * 根据流程定义Key和用户ID查询业务Id列表
	 * @param pdkey
	 * @param ByUserId   登陆用户ID
	 * @return
	 */
	public List<Long> getBusinessIdListByUserId(String pdkey,String userId){
		List<Long> ids=new ArrayList();
		//获得任务服务对象
		TaskService taskService = processEngine.getTaskService();
		//获得运行时服务对象
		RuntimeService runtimeService = processEngine.getRuntimeService();
		//通过任务对象，以流程实例key和任务节点key为条件进行查询，得到任务列表
		TaskQuery taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey(pdkey);//流程定义KEY 查询条件  buy
		taskQuery.taskCandidateUser(userId);//以用户ID作为查询条件
		
		List<Task> list = taskQuery.list();
		
		//循环遍历任务列表，得到每一个任务
		for(Task task:list){
			//通过每一个任务得到流程实例ID，以流程实例ID为条件查询流程实例对象
			ProcessInstanceQuery pquery = runtimeService.createProcessInstanceQuery();
			pquery.processInstanceId(task.getProcessInstanceId());
			ProcessInstance p = pquery.singleResult();
			if(p.getBusinessKey()!=null){
				//通过流程实例对象，得到业务id
				ids.add(Long.valueOf(p.getBusinessKey()));
			}
		}
		
		return ids;
	}
	
	
	/**
	 * 历史任务查询
	 * @return
	 *//*
	public List<HistoricTaskInstance> getHistorivTaskInstance(int firstResult, int maxResults){
		//获得历史服务对象
		HistoryService historyService = processEngine.getHistoryService();
		//得到历史任务实例查询对象
		HistoricTaskInstanceQuery hquery = historyService.createHistoricTaskInstanceQuery();
		List<HistoricTaskInstance> list = hquery.listPage(firstResult, maxResults);
		
		return list;		
	}*/
	
	/**
	 * 历史任务查询（重载，按条件进行查询）
	 * @param activitiQuery
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<HistoricTaskInstance> getHistorivTaskInstance(ActivitiQuery activitiQuery, int firstResult, int maxResults){
		//调用自定义的得到HistoricTaskInstanceQuery的方法
		HistoricTaskInstanceQuery hquery = createQuery(activitiQuery);
		List<HistoricTaskInstance> list = hquery.listPage(firstResult, maxResults);
		return list;		
	}

	/**
	 * 得到历史任务的所有记录数
	 *//*
	public Long getHistoricTaskInstanceQueryCount() {
		HistoryService historyService = processEngine.getHistoryService();
		HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
		long count = query.count();
		return count;
	}*/
	
	/**
	 * 得到历史任务的所有记录数（方法重载，按条件查询记录）
	 */
	public Long getHistoricTaskInstanceQueryCount(ActivitiQuery activitiQuery) {
		//调用自定义的得到HistoricTaskInstanceQuery的方法
		HistoricTaskInstanceQuery query = createQuery(activitiQuery);
		long count = query.count();
		return count;
	}
	
	/**
	 * 自定义得到HistoricTaskInstanceQuery的方法，并在该方法内添加query查询所需的条件
	 * （这一点有点跟hibernate的离线查询有点像）
	 * @param activitiQuery
	 * @return
	 */
	public HistoricTaskInstanceQuery createQuery(ActivitiQuery activitiQuery){
		//获得历史服务对象
		HistoryService historyService = processEngine.getHistoryService();
		//得到历史任务实例查询对象
		HistoricTaskInstanceQuery hquery = historyService.createHistoricTaskInstanceQuery();
		if(activitiQuery!=null){
			if(activitiQuery.getUserId()!=null && !activitiQuery.getUserId().equals("")){
				hquery.taskAssignee(activitiQuery.getUserId());
			}
			if(activitiQuery.getDate1()!=null){
				hquery.taskCompletedAfter(activitiQuery.getDate1());
			}
			if(activitiQuery.getDate2()!=null){
				hquery.taskCompletedBefore(activitiQuery.getDate2());
			}			
		}
		return hquery;
	}
	
	/**
	 * 部署流程图
	 * @param file
	 * @throws FileNotFoundException 
	 */
	public void deploy(File file) throws FileNotFoundException{
		InputStream input = new FileInputStream(file);
		ZipInputStream zipInput = new ZipInputStream(input);
		
		RepositoryService repositoryService = processEngine.getRepositoryService();
		DeploymentBuilder deployment = repositoryService.createDeployment();
		deployment.addZipInputStream(zipInput);
		
		deployment.deploy();  //部署
	}
	
	/**
	 * 得到流程实例列表
	 * @return
	 */
	public List<ProcessInstance> getProcessInstanceList(){
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstanceQuery instanceQuery = runtimeService.createProcessInstanceQuery();
		List<ProcessInstance> list = instanceQuery.list();
		
		return list;
	}
	
	
	/**
	 * 将图片信息写入到输出流中
	 * @param os   输出流
	 * @param processDefId  流程定义ID
	 * @throws IOException
	 */
	public void writePngToOs(OutputStream os,String processDefId,String activitiId) throws IOException{
		//需求：将图片信息得到，然后写入输出流中
		
		//得到仓库服务对象
		RepositoryService service = processEngine.getRepositoryService();
		//根据流程定义ID得到流程定义对象
		ProcessDefinition pd = service.getProcessDefinition(processDefId);
		//得到流程图的流
		InputStream input = service.getResourceAsStream(
				pd.getDeploymentId(), pd.getDiagramResourceName());
		//根据输入流，生成图片
		BufferedImage image = ImageIO.read(input);
		
		//获得当前任务节点的坐标
		ProcessDefinitionEntity pde = (ProcessDefinitionEntity) pd;
		ActivityImpl ai = pde.findActivity(activitiId);
		
		//绘制矩形
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.red);
		graphics.drawRect(ai.getX(), ai.getY(), ai.getWidth(), ai.getHeight());
		graphics.dispose();
		
		ImageIO.write(image, "PNG", os);
	}
}
