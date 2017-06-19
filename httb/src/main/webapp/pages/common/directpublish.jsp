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
	
});

//查询方法
function publishTask(type){
	var url = "<c:url value='/directpublish/publish.html' />";
	$.ajax({
		url : url ,
		data : {'type':type},
		dataType : 'json',
		type : 'POST',
		success : function(data) {
           if(data.success){
        	   alert("实时发布"+type+"成功！");
           }
           else{
        	   alert("实时发布"+type+"失败！");
           }
		}
	});
}
</script>

<!-- 列表内容域 -->
<div class="content" style="height: 265px;">
	
	    <table id="list_table" class="list_table">
           <tr>
             <td style="width: 50%"><a href="#" onclick="publishTask('categoryTask');">刷新知识树</a></td>
             <td><a href="#" onclick="publishTask('quesToCateTask');">刷新试题</a></td>
           </tr>      
            <tr>
             <td style="width: 50%"><a href="#" onclick="publishTask('moniTiPaperTask');">刷新模拟卷</a></td>
             <td><a href="#" onclick="publishTask('zhenTiPaperTask');">刷新真题卷</a></td>
           </tr>  
           <tr>
             <td style="width: 50%"><a href="#" onclick="publishTask('examAnalysisTask');">刷新试卷分析</a></td>
             <td><a href="#" onclick="publishTask('questionAnalysisTask');">刷新试题分析</a></td>
           </tr>    
           <tr>
             <td style="width: 50%"><a href="#" onclick="publishTask('initSXRankAnalysis');">刷新排名</a></td>
             <td></td>
           </tr>       
        </table>

</div>


