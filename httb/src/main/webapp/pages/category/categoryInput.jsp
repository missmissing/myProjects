<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<title></title>
<head>
<!-- 引入公共页面 -->
<%@ include file="/common/common.jsp"%>

<script type="text/javascript">
	/**进入到试题添加页*/
	function goback() {
		var url = "<c:url value='/category/list.html' />";
		//window.parent.document.getElementById("content_ifr").src = url;
		parent.openIframePage("",url);
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
			<form action="/imagemanage/add.action" id="imageForm" method="post" accept-charset="utf-8" enctype="multipart/form-data">
				<div style="display: none">
					<input type="hidden" name="dilicms_csrf_token" value="" />
				</div>
				<table class="form_table">
					<col width="150px" />
					<col />
					<tr>
						<th>导入知识点：</th>
						<td ><%@include file="/common/upload/uploadCategory.jsp"%>
						</td>
					</tr>
					<tr>
						<th><div id="category_result_name"></div></th>
						<td >
							<div id="category_result_val"></div>
						</td>
					</tr>
					<tr>
						<th><div id="category_error_name"></div></th>
						<td >
							<div id="category_error_val"></div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>

