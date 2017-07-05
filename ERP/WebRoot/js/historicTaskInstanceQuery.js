$(function(){
	$('#grid').datagrid({
		url:'activiti_historicTaskInstanceQuery.action',
		columns:[[
		          {field:'processDefinitionId',title:'流程定义ID',width:200},
		          {field:'processInstanceId',title:'流程实例ID',width:200},
		          {field:'name',title:'任务名称',width:200},
		          {field:'taskDefinitionKey',title:'任务定义Key',width:200},
		          {field:'endTime',title:'操作时间',width:200,formatter:function(value){
		        	  return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
		          }},
		          {field:'assignee',title:'执行人',width:200,formatter:function(value){
		        	  return emp[value];
		          }},
		       ]],
		pagination:true
	});
	
	$('#btnSearch').bind('click',function(){
		var formdata = getFormData('searchForm');
		$('#grid').datagrid('load',formdata);
	});
})
