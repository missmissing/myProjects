<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${TITLE }</title>
<%@ include file="/common/common.jsp"%>
<script type="text/javascript">
$(function() {
	//正常选择题--单选
	$("#onenormal").bind("click", function() {
		var url = "<c:url value='/question/addNormal.html' />";
		parent.openIframePage("添加试题",url);
	});
	//正常选择题--多选
	$("#morenormal").bind("click", function() {
		var url = "<c:url value='/question/addNormal.html' />";
		parent.openIframePage("添加试题",url);
	});
	//共用题干
	$("#sharecontent").bind("click", function() {
		var url = "<c:url value='/question/addShareContent.html' />";
		parent.openIframePage("添加共用题干试题",url);
	});
	//共用选项
	$("#shareoption").bind("click", function() {
		var url = "<c:url value='/question/addShareOption.html' />";
		parent.openIframePage("添加共用选项试题",url);
	});
	//返回试题列表
	$("#returnList").bind("click", function() {
		var url = "<c:url value='/question/list.html' />";
		parent.openIframePage("",url);
	});
	
	
});
</script>
<style>
.questype {
 	font-size:15px;
    cursor:pointer;
	padding: 8px;
	margin: 20px;
	width: 100px;
	text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
	background-color: #F2F2F2;
	border: 1px solid #fbeed5;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	*zoom: 1
}
.questype:HOVER{
	background-color: #F2900D;
	color: #FFFFFF;
}

.selecttype {
    font-size:18px;
	text-align: center;
	font-weight : bold;
	color: #468847;
	padding: 8px;
	margin: 20px;
}
</style>
<div class="headbar">
	<div class="operating" style="overflow: visible">
		<a class="hack_ie" href="###" id="returnList">
		   <button class="operating_btn" type="button" >
				<span>返回列表</span>
			</button>
		</a>
	</div>
</div>
<div class="content_box">

	<div class="content">
		<div class="selecttype">试题类型</div>
		<input type="button" class="questype" id="onenormal" value="单选题" /> 
		<input type="button" class="questype" id="morenormal" value="多选题" /> 
		<input type="button" class="questype" id="sharecontent" value="共用题干题" /> 
		<input type="button" class="questype" id="shareoption" value="共用选项题" />
	</div>
</div>
