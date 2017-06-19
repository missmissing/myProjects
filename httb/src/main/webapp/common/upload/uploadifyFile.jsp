<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
	<script type="text/javascript">
	// 弹出窗口选择文件
	var shddPop;
	function popupWin(inp){
		if(!shddPop){
			shddPop = new dhtmlXPopup({ mode: "bottom"});
			shddPop.attachObject("tb");
		}
		if (shddPop.isVisible()) {
			shddPop.hide();
		} else {
			var x = getAbsoluteLeft(inp);
			var y = getAbsoluteTop(inp);
			var w = inp.offsetWidth;
			var h = inp.offsetHeight;
			shddPop.show(x, y, w, h);
		}
	}
	</script>
	<input id="selFile" type="button" value="上传文件"/>
	<%--用来作为文件队列区域--%>
    <div id="tb" style="display:none; width: 400px; height: 100px;">
    	<input id="uploadify" type="file" name="uploadify"/>
    	<div style="display: none;">
    		<input type="hidden" id="fileName" class="uploadFile hideFileName" />
			<input type="hidden" id="filePath" class="uploadFile hideFilePath"/>
			<input type="hidden" id="oldFilePath"/>
			<input type="hidden" id="fileShow" class="uploadFile fileName" />
    	</div>
    </div>
<!-- 上传插件css样式 -->
<link href="${jsPath}/uploadify/uploadify.css" rel="stylesheet" type="text/css"/>
<!-- 上传插件核心js -->
<script src="${jsPath}/uploadify/jquery.uploadify-3.1.js" type="text/javascript"></script>
<script type="text/javascript">
	var fileStrs = '', urlStrs='';
	var rId, cIndex, cUrlIndex, myGrid;
	var flag = false, isHxm = false;
	$("#selFile").bind("click",function(){
		flag = false;
		clearFileNameAndUrl();
		popupWin(this);
	});
	setTimeout(function(){
		$("#uploadify").uploadify({
			'swf' : '${jsPath}/uploadify/uploadify.swf', 
			'uploader' : '${path}/scmUpload/uploadFile.action', 
			'cancelImg' : '${jsPath}/uploadify/uploadify-cancel.png',
			'multi' : true,
			'auto' : true,
			'buttonText' : '上传文件',
			'queueSizeLimit': '5',
			'fileSizeLimit': '204800',
			'width' : '80',
			'height' : '20',
			'rollover': true,
			'fileObjName'  : 'file', 
			'fileTypeExts' : '*.*',
			'fileTypeDesc' : '请选择',
			'onInit':function (){
				// 判断函数是否存在
	        	if("undefined" != typeof uploadInit)
	        		uploadInit();
			},'onUploadStart': function(file) {
				// 判断函数是否存在
	        	if("undefined" != typeof uploadParam){
	        		uploadParam();
	        		// 动态设置参数
					$("#uploadify").uploadify("settings", "formData", { 'oldFilePath': $(".hideFilePath").val() });
	        	}
			},'onUploadSuccess' : function(file, data, response) { 
	            var ary = data.split("#");
	            // 向文件名称隐藏域赋值
				var fileName = $("#fileName").val();
				// 向文件路径隐藏域赋值
				var filePath = $("#filePath").val();
				// 上传文件显示区域
				var fileShow = $("#fileShow").val();
				$("#fileName").val(fileName=="" ? file.name : file.name + "," + fileName);
				$("#filePath").val(filePath=="" ? ary[2] : ary[2] + "," + filePath);
				$("#fileShow").val(fileShow=="" ? file.name : file.name + "," + fileShow);
				// 判断函数是否存在
	        	if("undefined" != typeof uploadInit)
	        		uploadInit();
				var url = "${path}" + ary[2];
				var href = "<div class='ufNotImg' style='white-space:nowrap; overflow:hidden;'>";
					href += "<img class='deleteFile' onclick='deleteFile(this)' path='" + ary[2] + "' fileName='" + file.name
						 + "' title='移除' src='${themesPath}/icon/remove.png' style='cursor:pointer;width:12px;height:12px;'/>";
					href += "&nbsp;<a href='" + url + "'title='"+ ary[1] +"'>" + ary[1] + "</a>";
					href += "</div>";
				fileStrs += href;
				urlStrs += (urlStrs=="" ? "": "#") + ary[2];
			},'onQueueComplete' : function(queueData){
				// true:行项目附件,false:抬头附件
				if(flag){
					if(isEdit){
						var fileStr = myGrid.cells(rId,cIndex).getValue();
						var start = fileStr.indexOf(">");
						var stop = fileStr.lastIndexOf("</DIV>");
						var file = fileStr.substring(start+1,stop);
						var files = "<div style='white-space:nowrap; overflow-x:hidden; overflow-y:auto; height:28px;'>"
							  	  + file + fileStrs + "</div>";
						myGrid.cells(rId,cIndex).setValue(files);
					}else{
						var files = "<div style='white-space:nowrap; overflow-x:hidden; overflow-y:auto; height:28px;'>"
							  + myGrid.cells(rId,cIndex).getValue()+fileStrs + "</div>";
						myGrid.cells(rId,cIndex).setValue(files);
					}
					var currentUrl = myGrid.cells(rId,cUrlIndex).getValue();
					currentUrl = currentUrl==""?urlStrs:(currentUrl+"#"+urlStrs);
					myGrid.cells(rId,cUrlIndex).setValue(currentUrl);
				}
				else {
					var dhfj = $("#uploadFiles").html();
					var a = dhfj==""?fileStrs:(dhfj+fileStrs)
					$("#uploadFiles").html("");
					$("#uploadFiles").append(a);
				}
				fileStrs="";
				urlStrs="";
	        	if(shddPop && shddPop.isVisible())
	        		shddPop.hide();
			}
		});
	},10);
	// 下载文件
	function downLoad(inp){
		var path = $(inp).attr("path");
		var name = $(inp).attr("title");
		window.location.href = "${path}/scmUpload/download.action?filePaths=" + path
							 + "&fileName=" + encodeURI(encodeURI(name));
	}
	// 动态绑定删除事件
	function deleteFile(inp){
		// 要删除文件路径
		var path = $(inp).attr("path");
		var urls = myGrid.cells(rId,cUrlIndex).getValue();
		urls = urls.replace(path, "");
		var urlArry = urls.split("#");
		var urlStr="";
		if(urlArry.length>0)
			for(var i=0; i<urlArry.length; i++){
				if(urlArry[i]!=""){
					urlStr += (urlStr=="" ? "": "#") + urlArry[i];
				}
			}
		myGrid.cells(rId,cUrlIndex).setValue(urlStr);
		// 需要删除文件名称
		var fileName = $(inp).attr("fileName");
		// 删除链接
		var url = "${path}/scmUpload/deleteFile.action";
		$.post(url, {"path":path,"fileName":fileName}, function(data) {
			if (data.success) {
				// 移除"删除文件"的DIV
				$(inp).parent("div").remove();
				// 获取隐藏域上传文件名称,并且按","拆分
				var fileNames = $("#fileName").val().split(",");
				// 获取隐藏域上传文件路径,并且按","拆分
				var filePaths = $("#filePath").val().split(",");
				// 新文件名称
				var newFileName = "";
				// 新文件路径
				var newFilePath = "";
				// 循环上传文件名称
				$.each(fileNames,function(index,tx){
					if(tx != fileName){
						newFileName += newFileName == "" ? tx : "," + tx;
					}
				});
				// 循环上传文件路径
				$.each(filePaths,function(index,tx){
					if(tx != path){
						newFilePath += newFilePath == "" ? tx : "," + tx;
					}
				});
				$("#fileName").val(newFileName);
				$("#filePath").val(newFilePath);
			}
		}, "json");
		return false;
	}
	// 清楚隐藏Url和文件名
	function clearFileNameAndUrl(){
		$("#fileName").val("");
		$("#filePath").val("");
		$("#fileShow").val("");
	}
</script>
