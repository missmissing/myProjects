<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>华图在线题库-后台管理</title>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery/jquery-1.7.2.min.js' />"></script>
<link rel="SHORTCUT ICON" href="<c:url value='/web-resource/styles/common/images/ico.ico' />" media="all" />
<link rel="Bookmark" href="<c:url value='/web-resource/styles/common/images/ico.ico' />" media="all" />
<link rel="stylesheet" type="text/css" href="<c:url value='/web-resource/styles/theme/content.css' />" />
</head>

<script type="text/javascript">
	$(function() {
		//验证用户登录信息
		function valitLogin() {
			var userno = $("#userno").val();
			var password = $("#password").val();
			var msg = "";
			if (userno == "") {
				msg = "用户名不能为空！";
			} else if (password == "") {
				msg = "密码不能为空！";
			}
			return msg;
		}
		
		function login() {
			$.ajax({
				url : "<c:url value='/login/login.action' />",
				data : $("#loginForm").serialize(),
				dataType : 'json',
				type : 'POST',
				cache : false,
				success : function(data) {
					if (data.success) {
						location.href="<c:url value='/pages/common/main.jsp'/>";
						//window.navigate("/pages/common/main.jsp");
					} else {
						var meg = data.message;
						if(typeof(meg) =='undefined'){
							alert("系统连接错误，请联系管理员！");
						}else{
							alert(data.message);
						}
						return false;
					}
				}, 
				error : function(data) {
					var meg = data.message;
					if(typeof(meg) =='undefied'){
						alert("系统连接错误，请联系管理员！");
					}else{
						alert(data.message);
					}
					return false;
				},
				async:false
			});
		}
		$("#loginbtn").click(function() {
			var msg = valitLogin();
			if (msg != "") {
				alert(msg);
			} else {
				login();
			}
		});
		
		// 添加回车查询方法
		document.onkeydown = function (e) { 
			var theEvent = window.event || e; 
			var code = theEvent.keyCode || theEvent.which; 
			if (code == 13) { 
				$("#loginbtn").click(); 
			} 
		};
	});
</script> 

<body id="login">
	<div class="container">
		<div id="header">
			<div class="logo">
			</div>
		</div>
		<div id="wrapper" class="clearfix">
			<div class="login_box">
				<div class="login_title">后台管理登录</div>
				<div class="login_cont">
					<b style="color: red"></b>
					<form id="loginForm" method="post" accept-charset="utf-8">
						<div style="display: none">
							<input type="hidden" name="dilicms_csrf_token" value="" />
						</div>
						<table class="form_table">
							<col width="90px" />
							<col />
							<tr>
								<th>用户名：</th>
								<td><input id="userno" name="userno" autocomplete="off"  class="normal" type="text"  alt="请填写用户名" value=""/></td>
							</tr>
							<tr>
								<th>密码：</th>
								<td><input id="password"  name="password" class="normal" type="password" alt="请填写密码" value=""/></td>
							</tr>
							<tr>
								<th></th>
								<td>
								<input id="loginbtn" class="submit" type="button" value="登录" />
								<input id="canclebtn" class="submit" type="reset" value="取消" /></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div id="footer">
			By <a href="http://www.huatu.com/" target="_blank">huatu.com</a> Copyright &copy; 2015
		</div>
	</div>
</body>
</html>
