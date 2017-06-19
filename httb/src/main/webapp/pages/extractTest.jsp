<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${apitype}数据抽取页面</title>
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
//查询按钮
function funQuery(){
	var url = "<c:url value='/httbapi/extractdata/getdata' />";
	var pt = $("#pt").val();
	if(pt != ''){
		$.ajax({
			url : url ,
			data : {"pt":pt},
			dataType : 'json',
			type : 'POST',
			cache : false,
			success : function(response) {
				art.dialog.tips(response.message);
			}
		});
	}else{
		art.dialog.alert('抓取数据类型不能为空，请检查！');
	}
	
	
}
</script>
<!-- main区域   begin-->
<div class="headbar">
	    <!-- 操作域  -->
		<div class="operating" style="position:relative; overflow:visible;height: 75px; margin:100px;">
     	      <a href="javascript:void(0)" onclick="window.open('http://tkgwy.huatu.com/httb/httbapi/common/test');"><button class="operating_btn" type="button"><span class="sel_all">公务员</span></button></a>
     	      <a href="javascript:void(0)" onclick="window.open('http://tkjs.huatu.com/httb/httbapi/common/test');"><button class="operating_btn" type="button"><span class="sel_all">教师</span></button></a>
     	      <a href="javascript:void(0)" onclick="window.open('http://tksydw.huatu.com/httb/httbapi/common/test');"><button class="operating_btn" type="button"><span class="sel_all">事业单位</span></button></a>
     	     
     	     <div class="search f_r">
				<form id="serachQuestion" method="post">
				    <label>${apitype}----</label> 
				              抓取数据类型:
	              	<select id="pt" class="normal"  style="width: 150px;">
	              		<option value="" >-请选择-</option>
	                   	<option value="0" >抓取试题</option>
	                   	<option value="1" >抓取试卷</option>
				    </select>
				    <input type="button" class="submit" onclick="funQuery();" value="Send"/>
				</form>
			</div>
      	</div>
	</div>
</div>
