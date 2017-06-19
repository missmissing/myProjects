<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
	<link href="<c:url value='/web-resource/scripts/uploadify/uploadify.css' />" rel="stylesheet" type="text/css"/>
	<script src="<c:url value='/web-resource/scripts/uploadify/jquery.uploadify-3.1.js' />" type="text/javascript"></script>
	<table>
    	<tr>
    		<td ><div id="fileQueue"></div></td>
    		<td>
				<input type="hidden"  id="fileName"  value=""/>
				<input type="hidden" id="filePath"  value=""/>
				<input type="hidden" id="delFileName" style="width:150px;"/>
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

<script type="text/javascript">
	$(function (){
		setTimeout(function(){
			$("#uploadify").uploadify({
				'swf' : '<c:url value="/web-resource/scripts/uploadify/uploadify.swf"/>',
				'uploader' : '<c:url value="/imagemanage/upload"/>',
				'cancelImg' : '<c:url value="/web-resource/scripts/uploadify/uploadify-cancel.png"/>',
				'queueID' : 'fileQueue',
				'multi' : false,
				'auto' : true,
				'buttonText' : '选择文件',
				'queueSizeLimit ': '1',
				'fileSizeLimit': '1024',
				'width' : '80',
				'height' : '20',
				//'wmode': 'transparent',
				'rollover': true,
				'fileObjName'   : 'file',
				'fileTypeExts' : '*.jpg;*.jpeg;*.png;*.tiff;*.ico',
				'fileTypeDesc' : '请选择图片',
				'onUploadStart': function(file) {
					//手动指定图片名字
					var imgname = $("#imgname").val();

					//已保存图片名字
					var delFileName = $("#delFileName").val();
					var delStoreName = $("#newimgname").val();

					//如果手动指定了图片名字,则以手动指定的为主
					if("undefined" != typeof imgname && imgname.length > 0){
						$("#fileName").val(imgname);
					}
					// 动态设置参数
					$("#uploadify").uploadify("settings", "formData", {
						'delFileName': delFileName, 'delStoreName' : delStoreName, 'imgname' : imgname
					});
				},'onUploadSuccess' : function(file, data, response) {
		            var ary = data.split("#");

					//图片路径
					$("#filePath").val(ary[0]);
					//原图片名称(上传新图片后需要删除)
					$("#delFileName").val(ary[2]);
					//显示图片名称文本域置空
					$("#uploadShowName").empty();
					var imgname = "<div style='padding:10px;' >"+ary[1]+"</div>";
					//显示图片名称
					$("#uploadShowName").append($(imgname));
					//展示上传图片
					//$("#imgshow").css("display","block");
					//$("#imgshow").attr("src",ary[0]);
					// 判断函数是否存在
		        	if("undefined" != typeof afterUpload){
		        		//调用加载页面方法
						afterUpload(ary[0],ary[1],ary[2]);
		        	}
				}
			});
		},10);
	});
</script>
