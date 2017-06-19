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
		var url = "<c:url value='/imagemanage/list.html' />";
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
			<form action="/imagemanage/add.action" id="imageForm" method="post" accept-charset="utf-8" enctype="multipart/form-data">
				<div style="display: none">
					<input type="hidden" name="dilicms_csrf_token" value="" />
				</div>
				<table class="form_table">
					<col width="150px" />
					<col />
					<input type="hidden" class="normal" id="imgid" name="image.aid" value="${image.aid }" />
					<input type="hidden" class="normal" id="imgurl" name="image.imgurl" value="${image.imgurl }" />
					<input type="hidden" class="normal" id="newimgname" name="image.newimgname" value="${image.newimgname }" />
					<tr>
						<th>图片名称：</th>
						<td width="250">
							${image.imgname }
						</td>
					</tr>
					<tr>
						<th>存储名称：</th>
						<td width="250">
							${image.newimgname }
						</td>
					</tr>
					<tr>
						<th>图片URL：</th>
						<td width="250">
							${image.imgurl }
						</td>
					</tr>
					<tr>
						<th>图片年份：</th>
						<td >
							${image.fromyear }
						</td>
					</tr>
					<tr>
						<th>图片展示：</th>
						<td >
						<img src="${image.imgurl }" id="imgshow" height="120" width="100"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>

