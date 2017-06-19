<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<title></title>
<head>
<%@ include file="/common/common.jsp"%>

<script type="text/javascript">
	/**进入到试题添加页*/
	function goback() {
		var url = "<c:url value='/imagemanage/list.html' />";
		window.parent.document.getElementById("content_ifr").src = url;
	}

	function afterUpload(imgurl,imgname,newimgname){
		$("#imgurl").attr("value",imgurl);
		$("#imgname").attr("value",imgname);
		$("#newimgname").attr("value",newimgname);
	}

	// 新增保存事件处理,提交保存，关闭窗口，刷新父页面列表
	function saveimg() {
		// 校验通过才可以保存
		$.post("add.action", $("#imageForm").serialize(), function(response) {
			if (response.success) {
				//跳回查询页面
				goback();
			}else{
				alert(response.message);
			}

		}, "json");
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
						<td width="250"><input type="text" class="normal" id="imgname" name="image.imgname"
							 style="width: 150px" value="${image.imgname }" /></td>
						<td rowspan="3"><img src="${image.imgurl }" style="display:none" id="imgshow" height="120" width="100" /></td>
					</tr>
					<tr>
						<th>图片年份：</th>
						<td ><input type="text" class="normal" id="imgname" name="image.fromyear"
							 style="width: 150px" value="${image.fromyear }" /></td>
					</tr>
					<tr>
						<th>导入图片：</th>
						<td ><%@include file="/common/upload/uploadifyImg.jsp"%>
						</td>
					</tr>
					<tr>
						<th></th>
						<td colspan="2">
							<input type="button" class="submit" id="save" value="提   交" onclick="saveimg();"/>
							<input type="button" class="btn" value="取   消" onclick="goback();"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>

