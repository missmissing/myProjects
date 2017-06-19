<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${apitype}测试页面</title>
<%@ include file="/common/common.jsp"%>
<!-- dhtmlx.popup -->
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxcommon.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxpopup.js' />"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxpopup_dhx_terrace.css' />" media="all" />
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.windows/dhtmlxwindows.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.windows/dhtmlxcontainer.js' />"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/web-resource/scripts/dhtmlx.windows/dhtmlxwindows.css' />" media="all" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/web-resource/scripts/dhtmlx.windows/dhtmlxwindows_dhx_terrace.css' />" media="all" />
<script type="text/javascript">
$(function() {
	$('#port').change(function(){ 
		var port = $('#port option:selected').val();//选中的值
		if(port.indexOf("getTbversion") > 0){
			$("#example").val("license=123");
		}
		else if(port.indexOf("getTbClassList") > 0){
			$("#example").val("license=123");
		}
		else if(port.indexOf("getSubsetById") > 0){
			$("#example").val("id=392&area=123&userno=123&liense=123");
		}
		else if(port.indexOf("getQuestions") > 0){
			$("#example").val("id=392&area=123&liense=123");
		}
		else if(port.indexOf("getPapers") > 0){
			$("#example").val("attribute=1&tclassify=392&keyword=&area=86&count=20&pageindex=1&license=123");
		}
		else if(port.indexOf("getQuestionsById") > 0){
			$("#example").val("id=试卷ID&license=123");
		}
		
		else if(port.indexOf("getWrongQuestionListByUserId") > 0){
			$("#example").val("userno=xxx&license=123");
		}
		else if(port.indexOf("getErrorQuestions") > 0){
			$("#example").val("userno=xxx&qids=xxx&license=123");
		}
		else if(port.indexOf("setAnswerAndgetResult") > 0){
			$("#example").val("license=123&testpaper={point:200101100,queslist:[{iserror:0,qid:60506,qusanswer:[B],useranswers:[D],userno:907532},{iserror:0,qid:38669,qusanswer:[D],useranswers:[B],userno:907532},{iserror:0,qid:70944,qusanswer:[B],useranswers:[C],userno:907532},{iserror:-1,qid:84082,qusanswer:[D],useranswers:[],userno:907532},{iserror:0,qid:84085,qusanswer:[D],useranswers:[A],userno:907532},{iserror:0,qid:38748,qusanswer:[D],useranswers:[C],userno:907532},{iserror:1,qid:38690,qusanswer:[C],useranswers:[C],userno:907532},{iserror:0,qid:38696,qusanswer:[A],useranswers:[D],userno:907532},{iserror:1,qid:84266,qusanswer:[C],useranswers:[C],userno:907532},{iserror:0,qid:70943,qusanswer:[D],useranswers:[B],userno:907532}],second:9,type:1,userno:907532");
		}
       	
	}) 
});
//查询按钮
function funQuery(){
	//var ip = $('#ip option:selected').val();//IP
	var port = $('#port option:selected').val();//接口
	var parameter = $("#parameter").val();//参数
	var url = "<c:url value='/"+port+"?"+parameter+"' />";
	$.ajax({
		url : url ,
		data : '',
		dataType : 'json',
		type : 'POST',
		cache : false,
		success : function(pageJson) {
			$("#result").val( JSON.stringify(pageJson));
		}
	});
	
}
</script>
<!-- main区域   begin-->
<div class="headbar">
	    <!-- 操作域  -->
		<div class="operating" style="position:relative; overflow:visible;height: 75px; ">
     	      <a href="javascript:void(0)" onclick="window.open('http://tkyl.huatu.com/httb/httbapi/common/test');"><button class="operating_btn" type="button"><span class="sel_all">医疗</span></button></a>
     	      <a href="javascript:void(0)" onclick="window.open('http://tkjr.huatu.com/httb/httbapi/common/test');"><button class="operating_btn" type="button"><span class="sel_all">金融</span></button></a>
     	      <a href="javascript:void(0)" onclick="window.open('http://tkgwy.huatu.com/httb/httbapi/common/test');"><button class="operating_btn" type="button"><span class="sel_all">公务员</span></button></a>
     	      <a href="javascript:void(0)" onclick="window.open('http://tkjs.huatu.com/httb/httbapi/common/test');"><button class="operating_btn" type="button"><span class="sel_all">教师</span></button></a>
     	      <a href="javascript:void(0)" onclick="window.open('http://tksydw.huatu.com/httb/httbapi/common/test');"><button class="operating_btn" type="button"><span class="sel_all">事业单位</span></button></a>
     	     
     	     <div class="search f_r">
				<form id="serachQuestion" method="post">
				     <!-- <select id="ip" class="normal"  style="width: 80px;" name="question.qtype"  >
				     	<option value="" >-请选择-</option>
	                   	<option value="http://tkyl.huatu.com/httb" >医疗</option>
	                   	<option value="http://tkjr.huatu.com/httb" >金融</option>
	                   	<option value="http://tkgwy.huatu.com/httb" >公务员</option>
	                   	<option value="http://tkjs.huatu.com/httb" >教师</option>
	                   	<option value="http://tksydw.huatu.com/httb" >事业单位</option>
				     </select> -->
				    <input id="ip" type="text" class="file" value="${apitype}" readonly="readonly"/> 
				  	接口:
	              	<select id="port" class="normal"  style="width: 200px;">
	              		<option value="" >-请选择-</option>
	                   	<option value="/httbapi/common/getTbversions" >获取版本号</option>
	                   	<option value="/httbapi/category/getTbClassList" >获取获取题库分类列表</option>
	                   	<option value="/httbapi/category/getSubsetById" >获取某节点下的章节树</option>
	                   	<option value="/httbapi/category/getQuestions" >通过章节id下载试题</option>
	                   	<option value="/httbapi/paper/getPapers">获取试卷列表 </option>
	                   	<option value="/httbapi/paper/getQuestionsById" >根据试卷ID下载试题 </option>
	                   	
	                   	<option value="/httbapi/question/getWrongQuestionListByUserId" >获取错题集合 </option>
	                   	<option value="/httbapi/question/getErrorQuestions" >通过知识点Id获取其下错题列表 </option>
	                   	<option value="/httbapi/analyze/setAnswerAndgetResult" >提交答题返回分析结果 </option>
				    </select>
				            参数：
				   	<input id="parameter" type="text" class="file" style="width: 600px;"/>       
	              	<input type="button" class="submit" onclick="funQuery();" value="Send"/>
				</form>
			</div>
			<div class="search f_r" >
				             参数实例：
				   	<input id="example" type="text" class="file" value="" style="width: 650px;" />       
			</div>
      	</div>
      	<!-- 列表域 -->
      	<div class="field">
	</div>
    <textarea  id="result" rows="4" style="width: 98%; height: 100%;min-height: 500px;"></textarea>	
</div>
