<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>预览试卷选中的试题</title>
<%@ include file="/common/common.jsp"%>
<style>
#quescheckeddiv span{cursor:pointer;margin-top:5px;margin-right:5px;display:inline-block;padding:2px 4px;font-size:16px;font-weight:bold;line-height:17px;color:#fff;text-shadow:0 -1px 0 rgba(0,0,0,0.25);white-space:nowrap;vertical-align:baseline;-webkit-border-radius:4px;-moz-border-radius:4px;border-radius:4px} 
.qcnochecked{background-color:#8fc21d}
.qcchecked{background-color:#fd9701}
</style>
<script type="text/javascript">
$(function() {
	//初始化
	var cheDiv = "";
	for(var m=0;m<idArr.length;m++){
		var num = m+1;
		if(m==0){
			viewques(idArr[m]);
			cheDiv +="<span class='qcchecked' id='"+idArr[m]+"'>"+num+"</span>";
		}else{
			cheDiv +="<span class='qcnochecked' id='"+idArr[m]+"'>"+num+"</span>";
		}
	}
	$("#quescheckeddiv").append(cheDiv);
	
	//试题切换  点击样式切换
	$("#quescheckeddiv span").click(function() {
		 $("#quescheckeddiv span").removeClass("qcchecked").addClass("qcnochecked");
		 $(this).addClass("qcchecked");
		 viewques(this.id);
	});
});
//预览单个试题
function viewques(id){
	var url = "<c:url value='/paper/viewques.html?qid="+encodeURIComponent(id)+"' />";
	document.getElementById("viewques_ifr").src = url;
}
</script>
<fieldset class="content_box" >
   <div id="quescheckeddiv" style="margin: 5px;text-align: left;">
   </div>
</fieldset>
<iframe style="display: block;width: 100%;height: 100%;" id="viewques_ifr" frameborder="0" scrolling="no"> </iframe>
