<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询商品列表</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript">
//删除 
function delProducts(){
	
	//jquery提交Form表单
	$("#jvForm").attr("action","${pageContext.request.contextPath }/product/delete.do");
	$("#jvForm").attr("method","post").submit();
}
</script>
</head>
<body> 
<form action="${pageContext.request.contextPath }/product/list.do" method="post">
查询条件：
<table width="100%" border=1>
<tr>
<td>
<input type="text" name="name">
</td>
<td>
	<select name="type">
		<c:forEach items="${maps }" var="map">
			
			<option value="${map.key }">${map.value }</option>
		</c:forEach>
	</select>
</td>
<td><input type="submit" value="查询"/></td>
</tr>
</table>
</form>
商品列表：
<form id="jvForm">
<table width="100%" border=1>
<tr>
	<td>商品名称</td>
	<td>商品价格</td>
	<td>生产日期</td>
	<td>商品描述</td>
	<td>操作</td>
</tr>
<c:forEach items="${itemsList }" var="item">
<tr>
	<td><input type="checkbox" name="ids" value="${item.id}"></td>
	<td>${item.name }</td>
	<td>${item.price }</td>
	<td><fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH-mm-ss"/></td>
	<td>${item.detail }</td>
	
	<td><a href="toEdit.do?id=${item.id}">修改</a></td>

</tr>
</c:forEach>

</table>
</form>
<input type="button" value="删除" onclick="delProducts()">
</body>

</html>