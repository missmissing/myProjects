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
	$("select").multiselect({
		   selectedText: "# of # selected"
		});
});

	/**进入到用户添加页*/
	function goback() {
		var url = "<c:url value='/user/list.html' />";
		window.parent.document.getElementById("content_ifr").src = url;
	}
	
	function save() {
		if($("#orgname").val()==""||$("#userno").val()==""||$("#password").val()==""||$("#phone").val()==""||$("#email").val()==""||$("#uname").val()==""){
			alert("您有未输入的必填项！");
			return false;
		}
		if($("#usernoExist").html()!=""||$("#emailRight").html()!=""||$("#passwordRight").html()!=""||$("#phoneRight").html()!=""){
			alert("请再次检查您的输入项是否正确！");
			return false;
		}
		
		$.ajax({
			url : "<c:url value='/user/save.action' />",
			data : $("#addForm").serialize(),
			dataType : 'json',
			type : 'POST',
			cache : false,
			success : function(data) {
				if (data.success) {
					location.href="<c:url value='/user/list.html'/>";
				} else {
					var meg = data.message;
					if(typeof(meg) =='undefined'){
						alert("添加用户信息错误，请联系管理员！");
					}else{
						alert(data.message);
					}
					return false;
				}
			}, 
			error : function(data) {
				var meg = data.message;
				if(typeof(meg) =='undefied'){
					alert("添加用户信息错误，请联系管理员！");
				}else{
					alert(data.message);
				}
				return false;
			},
			async:false
		});
	}
	
	function loadroles(){
		$.ajax({                    
			url : "<c:url value='/role/getroleOpts.action'/>",
			data : "",
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
						   selectedText: "# of # selected"
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
	
	function usernoIsExist(){
		var userno = $("#userno").val();
		$.ajax({                    
			url : "<c:url value='/user/usernoIsExist.action' />",
			data : 'userno='+userno,
			dataType : 'json',
			type : 'POST',
			success : function(data) {
				if (data.success) {
					if(data.data == 1){
						$("#usernoExist").html("登录名已经存在！");
					}else{
						$("#usernoExist").html("");
					}
				} 
			}
		});
		
	}
	
	function checkEmail(){
		var str = $("#email").val();
	    var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/
	    if(re.test(str)){
	    	$("#emailRight").html("");
	    }else{
	    	$("#emailRight").html("邮箱格式错误！");
	    }
	}
	
	function checkPassword(){
		var password = $("#password").val();
		var repassword = $("#passagain").val();
		if(password != repassword){
			$("#passwordRight").html("2次密码输入不一致！");
		}
		else{
			$("#passwordRight").html("");
		}
	}
	
	function checkPhone() {
		var str = $("#phone").val(); 
	    var re = /^1\d{10}$/
	    if (re.test(str)) {
	    	$("#phoneRight").html("");
	    } else {
	    	$("#phoneRight").html("手机格式错误！");
	    }
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
			<form id="addForm" name="addForm">
				<table class="form_table">
					<col width="150px" />
					<col />
				   <tr>
						<th>组织机构：</th>
						<td><input id="orgname" name="organization.name" class="normal" style="width: 150px"
							value="${user.organization.name}" type="text" 
							onclick="loadOrgTree();" readonly="true" />
							<span style="color: #FF0000" >*</span>
						</td>
						<input id="orgid" name="organization.id" value="${user.organization.id}" type="hidden"/>
					</tr>
					<input type="hidden" class="normal" id="id" name="id" value="${user.id}" />
					<tr>
						<th>显示名称：</th>
						<td width="160px"><input type="text" class="normal" id="uname"
							name="uname" style="width: 150px" value="${user.uname}" />
							<span style="color: #FF0000" >*</span>
							</td>
					</tr>
					<tr>
						<th>登陆名称：</th>
						<td width="160px"><input type="text" class="normal" id="userno"
							name="userno" style="width: 150px" value="${user.userno}" onblur="usernoIsExist();" />
							<span style="color: #FF0000" >*</span>
							<span style="color: #FF0000" id="usernoExist"></span>
						</td>						
					</tr>
					<tr>
						<th>初始密码：</th>
						<td><input type="password" class="normal" id="password"
							name="password" style="width: 150px" value="${user.password}" />
							<span style="color: #FF0000" >*</span>
						</td>
					</tr>
					<tr>
						<th>确认密码：</th>
						<td><input type="password" class="normal" id="passagain"  onblur="checkPassword();"
							name="passagain" style="width: 150px" value="${user.password}" />
							<span style="color: #FF0000" >*</span>
							<span style="color: #FF0000" id="passwordRight"></span>	
						</td>
					</tr>
					
					<tr>
						<th>固定电话：</th>
						<td><input type="textarea" class="normal" id="tel"
							name="tel" style="width: 150px" value="${user.tel}" /></td>
					</tr>
					<tr>
						<th>手机：</th>
						<td><input type="textarea" class="normal" id="phone" maxlength="11" onblur="checkPhone();"
							name="phone" style="width: 150px" value="${user.phone}" />
							<span style="color: #FF0000" >*</span>
							<span style="color: #FF0000" id="phoneRight"></span>	
							</td>
					</tr>

					<tr>
						<th>电子邮件：</th>
						<td><input type="textarea" class="normal" id="email" onblur="checkEmail();"
							name="email" style="width: 150px" value="${user.email}" />
							<span style="color: #FF0000" >*</span>
						<span style="color: #FF0000" id="emailRight"></span>	
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
