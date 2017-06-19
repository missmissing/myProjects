<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<table>
	<tr>
		<td><div id="fileQueue"></div></td>
		<td></td>
	</tr>
	<tr>
		<td class="scmToolbarBtns">
			<div>
				<input id="file" type="file" name="file" style="width: 160px; "/>
			</div>
		</td>
		<td valign="top">
			<img src="../web-resource/styles/page/images/uploadimg.png"
			onclick="ajaxFileUpload();" style="width: 30px; height: 30px;cursor:pointer;" title="上传"/>
		<div id="uploadShowName"></div></td>
	</tr>
</table>
<!-- 上传插件css样式 -->
<link
	href="<c:url value='/web-resource/scripts/uploadify/uploadify.css' />"
	rel="stylesheet" type="text/css" />
<!-- 上传插件核心js -->
<script
	src="<c:url value='/web-resource/scripts/uploadify/jquery.uploadify-3.1.js' />"
	type="text/javascript"></script>
<script src="<c:url value='/web-resource/scripts/ajaxfileupload.js' />"
	type="text/javascript"></script>

<script type="text/javascript">
	function ajaxFileUpload() {

		var filepath = $("#file").val();
		var extStart = filepath.lastIndexOf(".");
		var ext = filepath.substring(extStart, filepath.length).toUpperCase();
		if (ext != ".TXT") {
			alert("只支持txt格式的文件！");
			return;
		}
		$("#popupDiv").show();
		$("#alertDiv").show();
		$.ajaxFileUpload({
			url : 'upload.html', //用于文件上传的服务器端请求地址
			secureuri : false, //是否需要安全协议，一般设置为false
			fileElementId : 'file', //文件上传域的ID
			dataType : 'json', //返回值类型 一般设置为json
			success : function(data, status) //服务器成功响应处理函数
			{
				$("#popupDiv").hide();
				$("#alertDiv").hide();
				//将返回对象转成json
				if (data.success) {
					//返回结果
					var message = data.message;
					if (message != null) {
						//清空原内容
						$("#ques_batchnum_name").text("");
						$("#ques_batchnum_val").text("");
						$("#ques_result_name").text("");
						$("#ques_result_val").text("");

						//设置新内容
						var messages = message.split("#");
						$("#ques_batchnum_name").text("导入批次:");
						$("#ques_batchnum_val").text(messages[1]);
						$("#batchnum").val(messages[1]);

						$("#ques_result_name").text("导入记录:");
						$("#ques_result_val").text("成功导入" + messages[0] + "导题");
					}
					var errorName = data.data;
					if (errorName == null) {
						$("#ques_error_name").text("");
						$("#ques_error_val").next().remove();
					} else {
						var encodeName = encodeURI(encodeURI(errorName));
						$("#ques_error_name").text("错题记录:");
						$("#ques_error_val").next().remove();
						$(
								"<a style='color:blue' href='download.html?fileName="
										+ encodeName

										+ "'>" + errorName + "<a>")
								.insertAfter("#ques_error_val");
					}
				}else{
					alert(data.message);
				}
			},
			error : function(data, status, e)//服务器响应失败处理函数
			{
				alert("服务器响应失败,请联系管理员！");
			}
		})
		return false;

	}
</script>
