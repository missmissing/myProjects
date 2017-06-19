<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${TITLE }</title>
<%@ include file="/common/common.jsp"%>

<!-- dhtmlx.popup -->
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxcommon.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxpopup.js' />"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxpopup_dhx_terrace.css' />" media="all" />

<script type="text/javascript">
$(function() {
	//初始化 异步加载分页
	query();
	//make_page_list(200,10,1,5);
});

//查询方法
function query(type){
	var url = "<c:url value='/statistics/query.html' />";
	$.ajax({
		url : url ,
		data : null,
		dataType : 'json',
		type : 'POST',
		success : function(list) {
			$("#currentNum").html(list[0]);
			$("#historyNum").html(list[1]);
		}
	});
}
</script>

<!-- 列表内容域 -->
<div class="content" style="height: 265px;">
	
	    <table id="list_table" class="list_table">
           <tr>
             <td style="width: 50%">当前在线人数：<span id="currentNum"></span></td><td>历史访问总人数：<span id="historyNum"></span></td>
           </tr>       
        </table>

</div>


