<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<!-- 引入公共页面 -->
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<title>${TITLE }</title>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery/jquery-1.7.2.min.js' />"></script>
<link rel="SHORTCUT ICON" href="<c:url value='/web-resource/styles/common/images/ico.ico' />" media="all" />
<link rel="Bookmark" href="<c:url value='/web-resource/styles/common/images/ico.ico' />" media="all" />
<link rel="stylesheet" type="text/css" href="<c:url value='/web-resource/styles/theme/content.css' />" />

<head>
<script type="text/javascript">
$(function() {
	loadroles();
});

	function loadroles(){
		$.ajax({                    
			url : "<c:url value='/role/getroleOpts.action'/>",
			data : $("#dataForm").serialize(),
			dataType : 'json',
			type : 'POST',
			cache : false,
			success : function(data) {
				if (data.success) {
					var innercontent="<select id='roleselect' name='roleselect' multiple>";
					innercontent+=data.data;
					innercontent+="</select>";
					$("#roles").html(innercontent); 
					$("select").multiselect({
						 selectedPosition: 'right',
						//selectedText: ,						
						noneSelectedText: "==请选择==",
				        checkAllText: "全选",
				        uncheckAllText: "全不选",
				        minWidth:200
						});
					
				} else {
					return false;
				}
			}, 
			error : function(data) {
				alert("error")
				return false;
			},
			async:false
		});
	}
	
function save(){
	var roleIds = $("#roleselect").val();	
	if(roleIds==""||roleIds==null){
		alert("请选择角色");
		return false;
	}
	var userId=$("#userid").val();
	$.ajax({                    
		url : "<c:url value='/user/save_use_role.action'/>",
		data : "userId="+userId+"&roleIds="+roleIds,
		dataType : 'json',
		type : 'POST',
		cache : false,
		success : function(data) {
			if (data.success) {
				location.href="<c:url value='/user/list.html'/>";
			} else {
				alert("配置用户角色错误，请联系管理员！");
				return false;
			}
		}, 
		error : function(data) {
			alert("配置用户角色错误，请联系管理员！");
			return false;
		},
		async:false
	});
}

function goback() {
	var url = "<c:url value='/user/list.html' />";
	window.parent.document.getElementById("content_ifr").src = url;
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
			<form id="dataForm" name="dataForm">
			<input type="hidden" id="userid" name="id" value="${user.id}" />
			<input type="hidden" id="viewText" name="viewText" value="${user.viewText}" />
				<table class="form_table">
					<col width="150px" />
					<col />
				   
					<tr>
						<th>选择角色：</th>
						<td>
							<span id="roles">
								<select id="roleselect" name="roleselect" multiple>
								</select>
							</span>
						</td>
					</tr>	
					
					<tr>
						<th></th>
						<td colspan="2"><input type="button" id="savebtn"
							name="savebtn" value="保  存" onclick="save();" /> <input
							type="button" class="canclebtn" value="取  消" onclick="goback();" />
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
<%@ include file="/common/widget/multiselect/multiselect.jsp"%>
