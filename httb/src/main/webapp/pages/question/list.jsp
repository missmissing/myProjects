<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${TITLE }</title>
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
	var dhxListWins;
	$(function() {
		funYearList_ht("qyear");//初始化年份列表
		queryList(); //初始化列表

		//初始化 dhx窗口
		var imgPath = "<c:url value='/web-resource/scripts/dhtmlx.windows/img/' />";
		dhxListWins = new dhtmlXWindows();
		dhxListWins.setSkin("dhx_terrace");
		dhxListWins.enableAutoViewport(true);
		dhxListWins.setImagePath(imgPath);
	});
	/**进入到试题添加页*/
	function openAddPage(){
		parent.openIframePage("选择试题类型","<c:url value='/question/add.html' />");
	}

	/**导入试题*/
	function quesInput(){
		parent.openIframePage("导入试题","<c:url value='/quesinput/quesinput.html?pageFrom=question' />");
	}
/*************************popup选择树  begin************************************************************************/
	/**该页面 知识点选择树 回调函数 方法名和参数 固定不变
	 *  和include page="../common/popupRadioTree.jsp 联合使用
	 */
	function onTreeRadioFun(e, treeId, treeNode) {
		$("#knowledgename").val(treeNode.name);
		$("#knowledgepoint").val(treeNode.id);
		treePopup.hide();

	}
/*************************popup选择树  end************************************************************************/
//批量删
function multi_delete(){
	var  checkArr = $("tbody :checkbox[checked]");
    if(checkArr.length<1){
    	alert("请选择试题");
    }else{
    	var url = "<c:url value='/question/deleteBatch.html' />";
    	funDeleteRowBatch(url,checkArr);
    }

}

//查询按钮
function funQuery(){
	$("#list_table").html(null);
	//document.getElementById('httbody').innerHTML='';
	queryList();
}
//重置按钮
function funClear(){
	$('#serachQuestion')[0].reset();
	$("#knowledgename").val("");
	$("#knowledgepoint").val("");
}
//编辑试题
function editQuestion(id){
	var url = "<c:url value='/question/edit.html?qid="+encodeURIComponent(id)+"' />";
	parent.openIframePage("修改试题",url);
}
//试题预览
function viewQuestion(id){
	var url = "<c:url value='/question/view.html?qid="+encodeURIComponent(id)+"' />";
	var height = $(window).height();
	var width = $(window).width();
	var winsView = dhxListWins.createWindow("winsView",0,0,width, height);
	winsView.setText("试题预览");
	winsView.attachURL(url,true);
	winsView.denyMove();
	winsView.button("minmax1").hide();
	winsView.button("park").disable();
}

//删除试题
function deleteQustion(id,inp){
	var url = "<c:url value='/question/delete.html' />";
	funDeleteRow(url,id,inp);
}

/************ 异步加载分页 begin****************/
function queryList(){
	var knowledgepoint = $("#knowledgepoint").val();//知识点
	var url = "<c:url value='/question/query4teacher.html' />";
	$.ajax({
		url : url ,
		data : $("#serachQuestion").serialize(),
		dataType : 'json',
		type : 'POST',
		cache : false,
		success : function(pageJson) {
			var tableJson = pageJson.rows;
			var tbodyDiv="<tbody id='httbody' >";
			for(var i=0;i<tableJson.length;i++){
				tbodyDiv+="<tr>";
				var status = typeof(statusJsonMap[tableJson[i].qstatus]) == "undefined"?"":statusJsonMap[tableJson[i].qstatus];

				if(status != "发布"){
					tbodyDiv+="<td style='width: 35px;'><input type='checkbox' name='id[]' value='"+tableJson[i].qid+"' /></td>";
				}else{
					tbodyDiv+="<td style='width: 35px;'></td>";
				}
				var qcontent = tableJson[i].qcontent == null ? "" : tableJson[i].qcontent;
				tbodyDiv+="<td style='width: 220px;'>"+qcontent+"</td>";
				if(tableJson[i].qyear==null||tableJson[i].qyear=="null"){
					tbodyDiv+="<td>无</td>";
				}
				else{
					tbodyDiv+="<td>"+tableJson[i].qyear+"</td>";
				}
				var type = typeof(quetypesJsonMap[tableJson[i].qtype]) == "undefined"?"":quetypesJsonMap[tableJson[i].qtype];
				tbodyDiv+="<td>"+type+"</td>";
				var attribute = typeof(attributeJsonMap[tableJson[i].qattribute]) == "undefined"?"":attributeJsonMap[tableJson[i].qattribute];
				tbodyDiv+="<td>"+attribute+"</td>";
				tbodyDiv+="<td>"+status+"</td>";
				tbodyDiv+="<td>"+funDateFormat(tableJson[i].createtime)+"</td>";
				tbodyDiv+="<td>"+
							"<p class='betn_check' title='预览' onclick='viewQuestion(\""+tableJson[i].qid+"\")'/>";
				if(status != "发布"){
					tbodyDiv+="<p class='betn_edit' title='修改' onclick='editQuestion(\""+tableJson[i].qid+"\")' />";
					tbodyDiv+="<p class='betn_del' title='删除' onclick='deleteQustion(\""+tableJson[i].qid+"\",this)'/>";
				}

				tbodyDiv+="</td>";
				tbodyDiv+="</tr>";
			}
			tbodyDiv+="</tbody>";
			$("#list_table").html(null);
			$("#list_table").append(tbodyDiv);
			lineColour_ht();//隔行变色
		}
	});
}
/************ 异步加载分页 end****************/
</script>
<!-- main区域   begin-->
<div class="headbar">
	    <!-- 操作域  -->
		<div class="operating" style="position:relative; overflow:visible ">
  	    	 <a href="javascript:void(0)" onclick="selectAll('id[]');"><button class="operating_btn" type="button"><span class="sel_all">全选</span></button></a>
     	     <a href="javascript:void(0)" onclick="multi_delete();"><button class="operating_btn" type="button"><span class="delete">批量删除</span></button></a>
		 	 <a href="javascript:void(0)" onclick="openAddPage();"><button class="operating_btn" type="button"><span class="addition">添加</span></button></a>
     	     <a href="javascript:void(0)" onclick="quesInput();"><button class="operating_btn" type="button"><span class="export">导入</span></button></a>
     	     <div class="search f_r">
				<form id="serachQuestion" method="post">
					知识点:
					<input id="knowledgename" type="text" class="file" onclick="funPopupCheckboxTree(this,null);" readonly="readonly" style="width: 100px;"/>
					<input id="knowledgepoint" type="hidden" name="knowledgepoint" />
				            批次号：
				   	<input id="qbatchnum" type="text" class="file" style="width: 50px;" name="question.qbatchnum"/>
					年份:
					<select class="normal" style="width: 80px;"  id="qyear" name="question.qyear">
	              	</select>
	              	 地区:
	              	<select id="qarea" class="normal"  style="width: 80px;" name="question.qarea"  >
	                   	<option value="" >-请选择-</option>
				        <c:forEach items="${areaMap}" var="type" varStatus="status">
							<option value="${type.key}">${type.value}</option>
					    </c:forEach>
				    </select>
				   	 题型:
	              	<select id="qtype" class="normal"  style="width: 80px;" name="question.qtype"  >
	                   	<option value="" >-请选择-</option>
				        <c:forEach items="${quetypesMap}" var="type" varStatus="status">
							<option value="${type.key}">${type.value}</option>
					    </c:forEach>
				    </select>
				  	属性:
	              	<select id="qattribute" class="normal"  style="width: 80px;" name="question.qattribute" >
	                   	<option value="" >-请选择-</option>
				        <c:forEach items="${attributeMap}" var="type" varStatus="status">
							<option value="${type.key}">${type.value}</option>
					    </c:forEach>
				    </select>
				              状态:
	              	<select id="qstatus" class="normal"  style="width: 80px;" name="question.qstatus" >
	                   	<option value="" >-请选择-</option>
				        <c:forEach items="${statusMap}" var="type" varStatus="status">
							<option value="${type.key}">${type.value}</option>
					    </c:forEach>
				    </select>
	              	<input type="button" class="submit" onclick="funQuery();" value="搜索"/>
	              	<input type="button" class="btn" onclick="funClear();" value="重置" />
				</form>
			</div>
      	</div>
      	<!-- 列表域 -->
      	<div class="field">
      		<!-- 列头信息 -->
		<table class="list_table">
			<col width="40px" /><col/>
			<thead>
				<tr>
                	 <th></th>
                	 <th style="width: 220px;">题干</th>
					 <th>年份</th>
    				 <th>题型</th>
    				 <th>属性</th>
    				 <th>状态</th>
    				 <th>创建时间</th>
    				 <th>操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<!-- 列表内容域 -->
<div class="content" style="height: 350px;">
	<form action="" method="post" accept-charset="utf-8" id="content_list_form">
		<div style="display:none">
			<input type="hidden" name="dilicms_csrf_token" value="" />
		</div>
	    <table id="list_table" class="list_table">
			<col width="40px" /><col />
			<tbody>
	        </tbody>
      </table>
  </form>
</div>

<!-- 弹出知识点选择树 -->
<jsp:include page="../common/popupRadioTree.jsp" />
<script type="text/javascript">
var attributeJsonMap = toJson('${attributeJsonMap}');//属性
var quetypesJsonMap =toJson('${quetypesJsonMap}');//题型
var statusJsonMap= toJson('${statusJsonMap}');//状态
</script>
<!-- 分页条 -->
<!-- <div class="pagnation" id="pagnation"></div> -->
<!-- main区域   end-->
