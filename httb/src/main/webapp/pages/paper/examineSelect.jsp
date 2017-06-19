<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${TITLE }</title>
<%@ include file="/common/common.jsp"%>
<!-- 拖拽 -->
<link rel="stylesheet" type="text/css" href="<c:url value='/web-resource/scripts/jquery.ui/jquery-ui.min.css' />" media="all" />
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery.ui/jquery-ui.min.js' />"></script>
<!-- dhtmlx.win -->
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxcommon.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.windows/dhtmlxwindows.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.windows/dhtmlxcontainer.js' />"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/web-resource/scripts/dhtmlx.windows/dhtmlxwindows.css' />" media="all" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/web-resource/scripts/dhtmlx.windows/dhtmlxwindows_dhx_terrace.css' />" media="all" />

<style>
ul {list-style-type: none;margin: 0;padding: 0;margin-bottom: 10px;}
li {margin:3px;padding: 2px 0px 2px 2px;width: 70px;float:left;text-align: left;cursor: move;}
.tz{float:right;cursor: pointer;width: 12px;color: #FD9901;}
</style>
<script>
	var dhxSelectWins;
	var onequesid;//试题单个预览的试题iD
	var idArr = new Array();//选中试题id集合
	$(function() {
		funYearList_ht("qyear");//初始化年份列表
		//初始化 dhx窗口
		var imgPath = "<c:url value='/web-resource/scripts/dhtmlx.windows/img/' />";
		dhxSelectWins = new dhtmlXWindows();
		dhxSelectWins.setSkin("dhx_terrace");
		dhxSelectWins.enableAutoViewport(true);
		dhxSelectWins.setImagePath(imgPath);
		
		//拖拽
		$("#sortable").sortable({
			revert : true
		});
		$("#draggable").draggable({
			connectToSortable : "#sortable",
			helper : "clone",
			revert : "invalid"
		});
		$("ul, li").disableSelection();
		
		//初始化列表
		queryList(); 
	});
	//选中试题
	function checkdQuestion(qid,inp,type){
		$(inp).parents("tr").remove();//在列中删除
		var div="<li id=\""+qid+"\" class=\"ui-state-default\">"+type+"<span class=\"tz\" onclick=\"removequestion('"+qid+"');\">X</span></li>";
		$("#sortable").append(div);
	}
	//移除试题
	function removequestion(qid){
		$("#"+qid).remove();//在已选列表中移除
	}
	//预览单个试题
	function previewoneques(qid){
		onequesid = qid;
	    var text = $("#"+qid).text(); //显示的名字
	    var spantext = $(".tz").html();//span中的文字
		var url = "<c:url value='/pages/paper/viewques_one.jsp' />";
		var height = $(window).height()-100;
		var width = $(window).width()-100;
		var previewoneques = dhxSelectWins.createWindow("previewoneques",30,30,width, height);
		previewoneques.setText("【"+text.replace(spantext,"")+"】 试题预览");
		previewoneques.denyMove();
		previewoneques.attachURL(url,true);
		previewoneques.button("minmax1").hide();
		previewoneques.button("park").hide();
	}
	//查询按钮
	function funQuery(){
		$("#list_table").html(null);
		queryList();
	}
	//重置按钮
	function funClear(){
		$('#serachQuestion')[0].reset();
	}
	/************ 异步加载分页 begin****************/
	function queryList(){
		//获取已经选择的列表 start -----------
		idArr = new Array();
		var lis = $("#sortable li");
		for(var i=0;i<lis.length;i++){
			idArr[i] = lis[i].id;
		}
		//获取已经选择的列表 end--------------
		//$("tbody").remove();
		var url = "<c:url value='/question/query.html' />";
		$.ajax({
			url : url ,
			data : $("#serachQuestion").serialize(),
			dataType : 'json',
			type : 'POST',
			cache : false,
			success : function(pageJson) {
				var tableJson = pageJson.rows;
				var tbodyDiv="<tbody>";
				for(var i=0;i<tableJson.length;i++){
					//以下 判断试题未选中
					if(idArr.indexOf(tableJson[i].qid) == -1){
						tbodyDiv+="<tr>";
						var qcontent = tableJson[i].qcontent == null ? "" : tableJson[i].qcontent;
						tbodyDiv+="<td style='width: 300px;'>"+qcontent+"</td>";
						
						tbodyDiv+="<td>"+tableJson[i].qyear+"</td>";
						
						var type = typeof(quetypesJsonMap[tableJson[i].qtype]) == "undefined"?"":quetypesJsonMap[tableJson[i].qtype];
						tbodyDiv+="<td>"+type+"</td>";
						
						var attribute = typeof(attributeJsonMap[tableJson[i].qattribute]) == "undefined"?"":attributeJsonMap[tableJson[i].qattribute];
						tbodyDiv+="<td>"+attribute+"</td>";
						if(tableJson[i].qattribute == "0"){
							$("#pattribute").val("0");
						}else{
							$("#pattribute").val("1");
						}
						tbodyDiv+="<td>"+funDateFormat(tableJson[i].createtime)+"</td>";
						tbodyDiv+="<td>"+
							 		"<p class='betn_yes' title='选中' onclick='checkdQuestion(\""+tableJson[i].qid+"\",this,\""+type+"\");' />"+
							        "</td>";
						tbodyDiv+="</tr>";
					}
				}
				tbodyDiv+="</tbody>";
				$("#list_table").html(null);
				$("#list_table").append(tbodyDiv);
				lineColour_ht();//隔行变色
			}
		});
	}
	/************ 异步加载分页 end****************/
	
	/**该页面 知识点选择树 回调函数 方法名和参数 固定不变
	 *  和include page="../common/popupRadioTree.jsp 联合使用
	 */
	function onTreeRadioFun(e, treeId, treeNode) {
		$("#knowledgename").val(treeNode.name);
		$("#knowledgepoint").val(treeNode.id);
		treePopup.hide();

	}
	//预览选中试题
	function viewCheckQues(){
		idArr = new Array();
		var lis = $("#sortable li");
		for(var i=0;i<lis.length;i++){
			idArr[i] = lis[i].id;
		}
		var url = "<c:url value='/pages/paper/viewques.jsp' />";
		var height = $(window).height();
		var width = $(window).width();
		var winsViewQues = dhxSelectWins.createWindow("winsViewQues",0,0,width, height);
		winsViewQues.setText("试卷预览");
		winsViewQues.denyMove();
		winsViewQues.attachURL(url,true);
		winsViewQues.button("minmax1").hide();
		winsViewQues.button("park").hide();
	}
	//保存
	function savePaper(){
		var pid = $("#paperId").val();//试卷Id
		var qids = "";//试题id拼接字符串
		var lis = $("#sortable li");
		for(var i=0;i<lis.length;i++){
			if(i==lis.length-1){
				qids+=lis[i].id;
			}else{
				qids+=lis[i].id+",";
			}
		}
		$.post("<c:url value='/paper/updateQuesList.html' />",{"paper.pid":pid,"qids":qids,}, function(response) {
			if (response.success) {
				if(confirm("提交审核成功！"))
				{
				}
				var url = "<c:url value='/paper/examineList.html' />";
				parent.openIframePage("",url);
			}else{
				
			}

		}, "json");
	}
	
	//审核通过
	function passexamine(){
		var pattribute = $("#pattribute").val();
		var pid = $("#paperId").val();//试卷Id
		var qids = "";//试题id拼接字符串
		var lis = $("#sortable li");
		for(var i=0;i<lis.length;i++){
			if(i==lis.length-1){
				qids+=lis[i].id;
			}else{
				qids+=lis[i].id+",";
			}
		}
		$.post("<c:url value='/paper/passexamine.html' />",{"paper.pid":pid,"qids":qids,}, function(response) {
			if (response.success) {
				if(confirm("提交审核成功！"))
				{
				}
				var url = "<c:url value='/paper/examineList.html' />";
				parent.openIframePage("",url);
			}else{
				
			}

		}, "json");

	}
	
	//退回
	function unpassexamine(unpassexam){
		var pid = $("#paperId").val();//试卷Id
		var qids = "";//试题id拼接字符串
		var lis = $("#sortable li");
		for(var i=0;i<lis.length;i++){
			if(i==lis.length-1){
				qids+=lis[i].id;
			}else{
				qids+=lis[i].id+",";
			}
		}
		$.post("<c:url value='/paper/unpassexamine.html' />",{"paper.pid":pid,"qids":qids,"unpassexam":unpassexam,}, function(response) {
			if (response.success) {
				if(confirm("退回成功！"))
				{
				}
				var url = "<c:url value='/paper/examineList.html' />";
				parent.openIframePage("",url);
			}else{
				
			}

		}, "json");
		
	}
	
	//填写退回原因
	function unpassexam(){
		 var str=prompt("请填写退回原因！","");
		 str = str.trim();
		 if(str == ""){
			 alert("退回原因不能为空!");
			 return;
		 }
		 unpassexamine(str);
	}
	
</script>
<input type="hidden" id="paperId" value="${paper.pid}"></input>
</div>
	<fieldset class="content_box" >
    <legend >【${paper.ptitle}】已选试题</legend>
	<div class="content">
		<ul id="sortable">
			<c:forEach items="${quetypeMap}" var="qt" varStatus="status">
				<li id="${qt.key }" class="ui-state-default" title="双击预览" ondblclick="previewoneques('${qt.key}')">${status.count}-${qt.value}<span class="tz" onclick="removequestion('${qt.key}');">X</span></li>
			</c:forEach>
		</ul>
	</div>
    </fieldset>
<!-- main区域   begin-->
<div class="headbar" >
	    <!-- 操作域  -->
		<div class="operating" style="position:relative; overflow:visible;border-top: 0px;">
			 <a href="javascript:void(0)" onclick="viewCheckQues();"><button class="operating_btn" type="button" title="预览已选中试题"><span class="view">预览</span></button></a>
     	     <a href="javascript:void(0)" onclick="savePaper();"><button class="operating_btn" type="button" title="试卷保存"><span class="send">保存</span></button></a>
     	     <a href="javascript:void(0)" onclick="passexamine();"><button class="operating_btn" type="button" title="通过审核"><span class="grade">通过审核</span></button></a>
     	     <a href="javascript:void(0)" onclick="unpassexam();"><button class="operating_btn" type="button" title="退回"><span class="return">退回</span></button></a>
		 	 <div class="search f_r">
				<form id="serachQuestion" method="post">
					知识点:
					<input id="knowledgename" type="text" class="file" onclick="funPopupCheckboxTree(this,null);" readonly="readonly"/>
					<input id="knowledgepoint" type="hidden" name="knowledgepoint"/>
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
	              	<input type="button" class="submit" onclick="funQuery();" value="搜索"/>
	              	<input type="button" class="btn" onclick="funClear();" value="重置" />
				</form>
				<input type="hidden" id="pattribute"/>
			</div>
      	</div>
      	<!-- 列表域 -->
      	<div class="field">
      		<!-- 列头信息 -->
		<table class="list_table">
			<thead>
				<tr>
                	 <th style="width: 300px;">题干</th>
					 <th>年份</th>
					 <th>题型</th>
    				 <th>属性</th>
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
