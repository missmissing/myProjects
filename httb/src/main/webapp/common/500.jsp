<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>500</title>
		<%@ include file="/common/common.jsp"%>
	</head>
	
	<body>
		<div class="container-fluid">
			<div class="alert alert-error alert-block cter">
				<h1>500</h1>
				<h2>对不起！内部服务器错误！</h2>
				<div>
					您访问的内部服务器存在问题，请联系管理员！
					<br />
					<a href="#" class="btn-danger btn" onclick="window.history.go(-1);">返 回</a>
				</div>
			</div>
		</div>
	</body>
</html>
