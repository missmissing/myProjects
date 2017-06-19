<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
	<table>
    	<tr>
    		<td ><div id="fileQueue"></div></td>
    		<td>
    		</td>
    	</tr>
    	<tr>
    		<td class="scmToolbarBtns">
    			<ul>
					<li>
					    <div>
    		            	<input id="uploadify" type="file" name="uploadify"/>
					    </div>
			    	</li>
	    		</ul>
    		</td>
    		<td valign="top"><div id="uploadShowName" ></div></td>
    	</tr>
    </table>
<!-- 上传插件css样式 -->
<link href="<c:url value='/web-resource/scripts/uploadify/uploadify.css' />" rel="stylesheet" type="text/css"/>
<!-- 上传插件核心js -->
<script src="<c:url value='/web-resource/scripts/uploadify/jquery.uploadify-3.1.js' />" type="text/javascript"></script>

<script type="text/javascript">
	$(function (){
		setTimeout(function(){
			$("#uploadify").uploadify({
				'swf' : '<c:url value="/web-resource/scripts/uploadify/uploadify.swf"/>',
				'uploader' : '<c:url value="/category/upload"/>',
				'cancelImg' : '<c:url value="/web-resource/scripts/uploadify/uploadify-cancel.png"/>',
				'queueID' : 'fileQueue',
				'multi' : false,
				'auto' : true,
				'dataType' : 'json',
				'buttonText' : '选择文件',
				'queueSizeLimit ': '1',
				'fileSizeLimit': '1024',
				'width' : '80',
				'height' : '20',
				//'wmode': 'transparent',
				'rollover': true,
				'fileObjName'   : 'file',
				'fileTypeExts' : '*.txt',
				'fileTypeDesc' : '请选择文件',
				'onUploadStart': function(file) {


				},'onUploadSuccess' : function(file, data, response) {
					//将返回对象转成json
					var obj = eval('('+data+')');
					if(obj.success){
						$("#category_result_name").text("导入结果:");
						$("#category_result_val").text(obj.message);
						var errorName = obj.data;
						if(errorName == null){
							$("#category_error_name").text("");
							$("#category_error_val").text("");
						}else{
							var encodeName =  encodeURI(encodeURI(errorName));
							$("#category_error_name").text("试题导入错题记录:");
							$("<a href='download.html?fileName="+encodeName+"'>"+errorName+"<a>").insertAfter("#category_error_val");
						}
					}else{
						alert(obj.message);
					}
				}
			});
		},10);
	});
</script>
