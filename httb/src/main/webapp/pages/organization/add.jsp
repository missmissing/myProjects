<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<title></title>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery/jquery-1.7.2.min.js' />"></script>
<link rel="SHORTCUT ICON" href="<c:url value='/web-resource/styles/common/images/ico.ico' />" media="all" />
<link rel="Bookmark" href="<c:url value='/web-resource/styles/common/images/ico.ico' />" media="all" />
<link rel="stylesheet" type="text/css" href="<c:url value='/web-resource/styles/theme/content.css' />" />
<head>
<!-- 引入公共页面 -->
<%@ include file="/common/common.jsp"%>

<script type="text/javascript">
	/**进入到组织机构添加页*/
	function goback() {
		var url = "<c:url value='/organization/list.html' />";
		window.parent.document.getElementById("content_ifr").src = url;
	}
	
	function save() {
		if($("#orgname").val()==""||$("#name").val()==""||$("#organizationNo").val()==""){
			alert("您有未输入的必填项！");
			return false;
		}
		$.ajax({
			url : "<c:url value='/organization/save.action' />",
			data : $("#addForm").serialize(),
			dataType : 'json',
			type : 'POST',
			cache : false,
			success : function(data) {
				if (data.success) {
					location.href="<c:url value='/organization/list.html'/>";
				} else {
					var meg = data.message;
					if(typeof(meg) =='undefined'){
						alert("添加组织机构信息错误，请联系管理员！");
					}else{
						alert(data.message);
					}
					return false;
				}
			}, 
			error : function(data) {
				var meg = data.message;
				if(typeof(meg) =='undefied'){
					alert("添加组织机构信息错误，请联系管理员！");
				}else{
					alert(data.message);
				}
				return false;
			},
			async:false
		});
	}
	
	
	
	
</script>
</head>
<body>
	<div class="headbar">
		<div class="operating" style="overflow: visible">
			<a class="hack_ie" href="javascript:void(0);"><button
					class="operating_btn" onclick="goback();" type="button">
					<span>返回列表</span>
				</button></a>
		</div>
	</div>
	<div class="content_box">
		<div class="content form_content">
			<form  id="addForm" name="addForm">
				<table class="form_table">
					<col width="150px" />
					<col/>
					<input type="hidden" class="normal" id="id" name="id" value="${organization.id}" />
					<tr>
						<th>组织机构名称：</th>
						<td width="250">
						<input type="text" class="normal" id="name" name="name"
							 style="width: 150px" value="${organization.name}" />
							 <span style="color: #FF0000" >*</span>
						</td>
					</tr>
					<tr>
						<th>机构编号：</th>
						<td width="250">
						<input type="text" class="normal" id="organizationNo" name="organizationNo"
							 style="width: 150px" value="${organization.organizationNo}" />
							 <span style="color: #FF0000" >*</span>
						</td>
					</tr>
					<tr>
						<th>上级机构：</th>
						<td width="250">
						<input type="text" class="normal" id="orgname" name="parentOrg.name" onclick="loadOrgTree();" readonly="true"
							 style="width: 150px" value="${organization.parentOrg.name}" />
							 <span style="color: #FF0000" >*</span>
						</td>
						<input id="orgid" name="parentOrg.id" value="${organization.parentOrg.id}" type="hidden"/>
					</tr>
					<tr>
						<th>备注信息：</th>
						<td ><input type="textarea" class="normal" id="description" name="description"
							 style="width: 150px" value="${organization.description}" />
						</td>
					</tr>
					<tr>
						<th></th>
						<td colspan="2">
							<input type="button"  id="savebtn"    name="savebtn" value="保  存" onclick="save();"/>
							<input type="button"  class="canclebtn"    value="取   消"            onclick="goback();"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>
<%@ include file="/common/common.jsp"%>
<%@ include file="/pages/organization/organizationTree.jsp"%>
