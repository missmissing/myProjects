<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 修改子试题(共用题干) -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${TITLE }</title>
<%@ include file="/common/common.jsp"%>
<script type="text/javascript">
	var character = new Array("A","B","C","D","E","F","G","H","I","J");//允许这些选项
	$(function() {
		 var optionDiv ="";//选项代码拼接
		 var optionNumber = 0;//选项的个数
		//整理需要修改的数据
		 var tableData = $(tableObj).find("tr");
		 for(var i=0;i<tableData.length;i++){
			 var cells =  $(tableData[i]).children();
			 for(var j=0;j<cells.length;j++){
				  //获取td标签的class 和分别代表参数
				  var css =  $(cells[j]).attr("class");
				  var content = cells[j].innerHTML;//替换innerText
				  if(css =="ztgca"){//子题干
					  $("#qccontent").val(content);
				  }else if(css =="daca"){//答案
					  $("#qcans").val(content);
				  }else if(css =="jxca"){//解析
					  $("#qccomment").val(content);
				  }else if(css =="tzca"){//拓展
					  $("#qcextension").val(content);
				  }else if(css =="fzca"){//分值
					  $("#qcscore").val(content);
				  }else if(css =="xcca"){
				   	var optionCode =  character[optionNumber];
				   		optionNumber = optionNumber+1;
				    	optionDiv+="<ht>"+
						"<span class='badge' title='"+optionCode+"'>"+optionCode+"</span>";
						if(optionNumber>2){//默认两个选项不可以删除
							optionDiv+="<span class='optionDel' onclick='deleteOption(this);'>删除</span>";
						}
						optionDiv+="<br/>"+
						"<textarea class='isnostyle' id='"+optionCode+"'>"+content+"</textarea><br/>"+
					    "</ht>";
				   }
// 					<td class="ztgca">
// 					<td class="xcca">
// 					<td class="daca">
// 					<td class="jxca">
// 					<td class="tzca">
			 }
		}
		$("#optionDiv").append(optionDiv); 
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
	function updateChild(){
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
		
		$(tableObj).empty();//清空
		$(tableObj).append(zitableDiv);//追加
		/*******开始像父页面组装数据 end*/
		
		dhxWins.window("winsEdit").close();
		
		
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
					<!-- 以下样式部分用作定位  勿删除 ************************ -->
					<tr>
						<th style="vertical-align:top;" >选项 &nbsp;[<a href="javascript:void(0);" onclick="addOption();" style="color: red;">添加</a>]：</th>
						<td id="optionDiv">
							
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
							<input type="button" class="submit" onclick="updateChild();" value="修改"></input>
						</td>
					</tr>
					<tr style="height: 50px;"></tr>
				</table>
			</form>
		</div>
	</div>
