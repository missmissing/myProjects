<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 添加子试题(共用题干) -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${TITLE }</title>
<%@ include file="/common/common.jsp"%>
<script type="text/javascript">
	var character = new Array("A","B","C","D","E","F","G","H","I","J");//允许这些选项
	$(function() {
		validationEngine("childQuestionForm");//绑定formId检验表单
		var textareas = $("#childQuestionForm").find("textarea");
		for(var i=0;i<textareas.length;i++){
			var uploadUrl = "<c:url value='/imagemanage/ckEditorUpload.html' />";
			createCkEditor_ht(textareas[i],uploadUrl);
		}
	});
	/**添加新选项*/
	function addOption(){
		var newXuanXiang = "";//新选项标识
		var canshu =new Array();//已经有的参数数组
		var choices = $("#childQuestionForm").find("span[class='badge']");
		for(var i=0;i<choices.length;i++){
			canshu[i] = choices[i].title;
		}
		if(canshu.length>9){
			return ;
		}
		for(var i=0;i<character.length;i++){
			var xiabiao  = canshu.indexOf(character[i]);
			if(xiabiao < 0){ //不包含元素
				newXuanXiang = character[i];
				break;
			}
		}
		var optionDiv = "<ht><span class='badge' title='"+newXuanXiang+"'>"+newXuanXiang+"</span><span class='optionDel' onclick='deleteOption(this);'>删除</span><br/>"+
						"<textarea onclick='createEditor(this);' id='"+newXuanXiang+"'></textarea></br></ht>";
		$("#optionDiv").append(optionDiv);
		createEditor(newXuanXiang);//初始化 富文本
	}
	function deleteOption(inp){
		$(inp).parent().empty();
	}
	//save子题干信息
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
		
		var choices = $("#childQuestionForm").find("span[class='badge']");//选项编号
		var number = choices.length; //选项个数
		if(character[number-1] !=choices[number-1].title ){
			alert("选项顺序错误 请按ABC...排列");
			return;
		}
		var options = $("#childQuestionForm").find("textarea[class='isnostyle']");//选项集合
		for(var i=0;i<options.length;i++){
			var opeint = CKEDITOR.instances[options[i].id].getData();
			if(opeint == "" || opeint.length<=0){
				alert("选择"+options[i].id+"不能为空");
				return;
			}
		}
		
		/*******开始向父页面组装数据 begin*/
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
		for(var i=0;i<options.length;i++){
			var opeint = CKEDITOR.instances[options[i].id].getData();
			 zitableDiv+="<tr>"+
						 "<th >选项 "+options[i].id+"：</th>"+
						 "<td class='xcca'>"+opeint+"</td>"+
						 "</tr>";
		}
		zitableDiv+="<tr><th>答案：</th><td class='daca'>"+qcans+"</td></tr>";
		zitableDiv+="<tr><th>解析：</th><td class='jxca'>"+qccomment+"</td></tr>";
		zitableDiv+="<tr><th>拓展：</th><td class='tzca'>"+qcextension+"</td></tr>";
		zitableDiv+="<tr><th>分值：</th><td class='fzca'>"+qcscore+"</td></tr>";
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
						<th style="vertical-align:top;" >选项 &nbsp;[<a href="javascript:void(0);" onclick="addOption();" style="color: red;">添加</a>]：</th>
						<td id="optionDiv">
							<span class="badge" id="1" title="A">A</span><br/>
							<textarea class="isnostyle" id="A"></textarea><br/>
							<ht><span class="badge" id="2" title="B">B</span><br/>
							<textarea class="isnostyle" id="B"></textarea><br/>
							<ht>
								<span class="badge" id="3" title="C">C</span>
								<span class="optionDel" onclick="deleteOption(this);">删除</span>
								<br/>
								<textarea class="isnostyle" id="C"></textarea><br/>
							</ht>
							<ht>
								<span class="badge" id="4" title="D">D</span>
							    <span class="optionDel" onclick="deleteOption(this);">删除</span>
							    <br/>
							    <textarea  class="isnostyle" id="D"></textarea><br/>
							</ht>
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
