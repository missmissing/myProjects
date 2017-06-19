<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 添加子试题(共用选项) -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${TITLE }</title>
<%@ include file="/common/common.jsp"%>
<script type="text/javascript">
	$(function() {
		validationEngine("childQuestionForm");//绑定formId检验表单
		var textareas = $("#childQuestionForm").find("textarea");
		for(var i=0;i<textareas.length;i++){
			var uploadUrl = "<c:url value='/imagemanage/ckEditorUpload.html' />";
			createCkEditor_ht(textareas[i],uploadUrl);
		}
	});
	//添加子题干
	function addChild(){
		var qccontent = CKEDITOR.instances['qccontent'].getData();//子题干
		if(qccontent == "" || qccontent.length<=0){
			alert("子题干不能为空");
			return;
		}
		var qcans = $("#qcans").val();//答案
		if(qcans ==""){
			alert("答案不能为空");
			return;
		}
		var qccomment = CKEDITOR.instances.qccomment.getData();//解析
		var qcextension = CKEDITOR.instances.qcextension.getData();//拓展
		var qcscore = $("#qcscore").val();//分值
		
		/*******开始像父页面组装数据 begin*/
		var zitableDiv ="<table class='istablestyle'>"+
		                 "<tr>"+
						 "<td></td>"+
		                 "<td class='childTitle'>问题 "+ 
			             "[<a href='###' onclick='updatechildq(this);' style='color: #027F9D;'>修改</a>&nbsp;"+
			             "<a href='###' onclick='deletechildq(this);' style='color: red;'>删除</a>]"+
			             "</td><tr>"+
						 "<tr>"+
							"<th >子题干：</th>"+
							"<td class='ztgca'>"+qccontent+"</td>"+
						 "</tr>";
		zitableDiv+="<tr><th>答案：</th><td class='daca'>"+qcans+"</td></tr>";
		zitableDiv+="<tr><th>解析：</th><td class='jxca'>"+qccomment+"</td></tr>";
		zitableDiv+="<tr><th>拓展：</th><td class='tzca'>"+qcextension+"</td></tr>";
		zitableDiv+="<tr><th>分值：</th><td>"+qcscore+"</td></tr>";
		zitableDiv+="</table>";
		$("#zishitiDiv").append(zitableDiv);
		/*******开始像父页面组装数据 end*/
		dhxWins.window("winsAdd").close();
		
		
	}
</script>
	<div class="content_box">
		<div class="content form_content" style="height: 420px;overflow-y: scroll;">
			<form id="childQuestionForm" method="post" accept-charset="utf-8" enctype="multipart/form-data">
				<div style="display: none">
					<input type="hidden" name="dilicms_csrf_token" value="" />
				</div>
				<table class="form_table">
				     <col width="150px" />
					<col />
					<tr>
						<th >子题干：</th>
						<td>
							<textarea id="qccontent" class="validate[required]" ></textarea>
						</td>
					</tr>
					<tr>
						<th>答案：</th>
						<td>
							<input id="qcans" type="text" value="" class="normal validate[required]" />
						</td>
					</tr>
					<tr>
						<th >解析：</th>
						<td>
							<textarea id="qccomment" class="validate[required]" ></textarea>
						</td>
					</tr>
					<tr>
						<th >拓展：</th>
						<td>
							<textarea id="qcextension" class="validate[required]" ></textarea>
						</td>
					</tr>
					<tr>
						<th >分值：</th>
						<td>
							<input id="qcscore" class="validate[required]" />
						</td>
					</tr>
					<tr >
						<th></th>
						<td>
							<input type="button" class="submit" onclick="addChild();" value="添加"></input>
						</td>
					</tr>
					<tr style="height: 50px;"></tr>
				</table>
			</form>
		</div>
	</div>
