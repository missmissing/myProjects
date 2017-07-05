<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ERP管理系统 - 流程管理</title>
<link rel="stylesheet" type="text/css" href="ui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="ui/themes/icon.css">
<script type="text/javascript" src="ui/jquery.min.js"></script>
<script type="text/javascript" src="ui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/processInstanceList.js"></script>
</head>
<body>
<table class="easyui-datagrid" data-options="singleSelect:true,pagination:true">
	<thead>
	  <tr>
		<th data-options="field:'1',width:'200'">流程定义ID</th>
		<th data-options="field:'2',width:'200'">流程实例ID</th>
		<th data-options="field:'3',width:'200'">业务ID</th>
		<th data-options="field:'4',width:'200'">流程节点KEY</th>
		<th data-options="field:'5',width:'200'">操作</th>
	  </tr>
	</thead>
	
	<tbody>
	  <s:iterator value="#list">
	  	<tr>
	  		<td>${ProcessDefinitionId }</td>
	  		<td>${ProcessInstanceId }</td>
	  		<td>${BusinessKey }</td>
	  		<td>${ActivityId }</td>
	  		<td><a href="#" onclick="showProcessWindow('${processDefinitionId }','${activityId }')">查看流程图</a></td>	
	  	</tr>
	  </s:iterator>
	</tbody>
</table>

<!-- 流程图窗口 -->
<div id="processWindow" class="easyui-window" title="流程图" style="width: 700px;height: 500px" data-options="closed:true">
	<img id="processImg" alt="" src="">
</div>
</body>
</html>