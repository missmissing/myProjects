<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 修改共享题干试题-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${TITLE }</title>
<%@ include file="/common/common.jsp"%>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxcommon.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.windows/dhtmlxwindows.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.windows/dhtmlxcontainer.js' />"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/web-resource/scripts/dhtmlx.windows/dhtmlxwindows.css' />" media="all" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/web-resource/scripts/dhtmlx.windows/dhtmlxwindows_dhx_terrace.css' />" media="all" />
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery.multiselect/jquery.ui.core.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery.multiselect/jquery.ui.widget.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery.multiselect/jquery.multiselect.js' />"></script>
<link   rel="stylesheet" type="text/css"   href="<c:url value='/web-resource/styles/jquery.multiselect/jquery.multiselect.css' />" />
<link   rel="stylesheet" type="text/css"   href="<c:url value='/web-resource/styles/jquery.multiselect/jquery-ui.css' />" />

<script type="text/javascript">
	var dhxWins , tableObj;
	$(function() {
		
		var qcategory = '${question.qcategory }';
		 var objSelect = $("#qcategory").get(0);
		  for (var i = 0; i < objSelect.options.length; i++) {  
			  var str = $("#qcategory").get(0).options[i].value;
			  if(qcategory.indexOf(str) != -1){
				  $("#qcategory").get(0).options[i].selected = true;
			  }             
		  }
		
		$("#qcategory").multiselect({
			selectedPosition: 'right',
			noneSelectedText: "==请选择==",
	        checkAllText: "全选",
	        uncheckAllText: "全不选",
	        selectedList: 100
	        
		});
		
		
		validationEngine("questionForm");//绑定formId检验表单
		//退回
		$("#unpassExamine").bind("click", function() {
			var qcontent = CKEDITOR.instances['qcontent'].getData();//子题干
			if(qcontent == "" || qcontent.length<=0){
				alert("题干不能为空");
				return;
			}
			if ($("#questionForm").validationEngine("validate")) {
				$.post("<c:url value='/question/unpassExamine.html' />",{ jsonStr:serializeObjJson()}, function(response) {
					if (response.success) {
						if(confirm("退回成功！"))
						{
						}
						//parent.minWins();
						var url = "<c:url value='/question/examineList.html' />";
						parent.openIframePage("",url);
					}else{
						alert(response.message);
					}

				}, "json");
			}

		});
		
		//examine 审核通过
		$("#examine").bind("click", function() {
			var qcontent = CKEDITOR.instances['qcontent'].getData();//子题干
			if(qcontent == "" || qcontent.length<=0){
				alert("题干不能为空");
				return;
			}
			if ($("#questionForm").validationEngine("validate")) {
				$.post("<c:url value='/question/passExamine.html' />",{ jsonStr:serializeObjJson()}, function(response) {
					if (response.success) {
						if(confirm("审核通过成功！"))
						{
						}
						//parent.minWins();
						var url = "<c:url value='/question/examineList.html' />";
						parent.openIframePage("",url);
					}else{
						alert(response.message);
					}

				}, "json");
			}
		});
		//tab页 点击样式切换
		$("#othertab li").click(function() {
			 $("#othertab li").removeClass("active").addClass("");
			 $(this).addClass("active");
			 var herf = jQuery(this).children("a").attr('href');
			 $("#othertabPoint input[type='text']").removeClass("normal").addClass("tab-active");
			 $(herf).removeClass("tab-active");
			 $(herf).addClass("normal");
		});
		//返回试题列表
		$("#returnList").bind("click", function() {
			parent.minWins();
			var url = "<c:url value='/question/list.html' />";
			parent.openIframePage("",url);
		});
		
	});
	//页面加载完成后初始化 富文本编辑器 
	window.onload  = function(){ //children    
		var textareas = $("#questionForm").find("textarea");
		for(var i=0;i<textareas.length;i++){
			createEditor(textareas[i]);
		}
		//初始化 dhx窗口
		var imgPath = "<c:url value='/web-resource/scripts/dhtmlx.windows/img/' />";
		dhxWins = new dhtmlXWindows();
		dhxWins.setSkin("dhx_terrace");
		dhxWins.enableAutoViewport(true);
		dhxWins.setImagePath(imgPath);
	}
	/**生成富文本编辑器*/
	function createEditor(inp){
		tableObj = inp;
		var uploadUrl = "<c:url value='/imagemanage/ckEditorUpload.html' />";
		createCkEditor_ht(inp,uploadUrl);
		
	}
	/**该页面 知识点选择树 回调函数 方法名和参数 固定不变
	 *  和include page="../common/popupTree.jsp 联合使用
	 */
	function onTreeCheckFun(e, treeId, treeNode) {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getNodesByFilter(filter); // 查找叶子节点选择节点集合
		var qpointnames ="";
		var qpointids = "";
		for(var i=0;i<nodes.length;i++){
			qpointnames+= nodes[i].name+",";
			qpointids += nodes[i].id+",";
		}
		$("#qpointnames").val(qpointnames);
		$("#qpointids").val(qpointids);
	}
	//添加试题子集合（共用题干）
	function addchildQuestion(){
		var url = "<c:url value='/question/addchildquescontent.html' />";
		var height = $(window).height();
		var width = $(window).width();
		var winsAdd = dhxWins.createWindow("winsAdd",0, 0,width,height);
		winsAdd.denyMove();
		winsAdd.setText("添加试题子集");
		winsAdd.attachURL(url,true);
		winsAdd.setModal(true);
	}
	//修改子题干【 不走后台直接修改】
	function updatechildq(inp){
		tableObj = $(inp).parents(".istablestyle");
		var url = "<c:url value='/pages/question/contentchild/editchildquestion.jsp' />";
		var height = $(window).height();
		var width = $(window).width();
		var winsEdit = dhxWins.createWindow("winsEdit",0, 0,width,height);
		winsEdit.denyMove();
		winsEdit.setText("修改试题子集");
		winsEdit.attachURL(url,true);
		winsEdit.setModal(true);
	}
	//删除子题
	function deletechildq(inp){
		$(inp).parents(".istablestyle").remove(); 
	}
</script>
<div class="headbar">
	<div class="operating" style="overflow: visible">
		<a class="hack_ie" href="###" id="returnList">
		   <button class="operating_btn" type="button" >
				<span>返回列表</span>
			</button>
		</a>
		<div class="search f_r">		
			 <button id="maxWinbtn" class="btn" type="button" hidefocus="true"  onclick="parent.maxWins();$(this).hide();$('#minWinbtn').show();">
				<span class="max">全屏</span>
			 </button>
			 <button id="minWinbtn" style="display:none" class="btn" type="button" hidefocus="true"  onclick="parent.minWins();$(this).hide();$('#maxWinbtn').show();">
				<span class="max">退出全屏</span>
			 </button>
    	</div>
	</div>
</div>
	<div class="content_box">
		<div class="content form_content" style="height: 420px;overflow-y: scroll;">
			<form id="questionForm" method="post" accept-charset="utf-8" enctype="multipart/form-data">
				<input id="qid" type="hidden" value="${question.qid }"  />
				<div style="display: none">
					<input type="hidden" name="dilicms_csrf_token" value="" />
				</div>
				<table class="form_table">
				     <col width="150px" />
					<col />
					<tr>
						<th>地区：</th>
						<td>
							<select id="qarea" >
		                    	<option value="" >-请选择-</option>
						        <c:forEach items="${areaMap}" var="type" varStatus="status">
									<c:choose>
									<c:when test="${type.key == question.qarea}">
										<option value="${type.key}" selected="selected">${type.value}</option>
								    </c:when>
								    <c:otherwise>
										<option value="${type.key}">${type.value}</option>
								    </c:otherwise>
								    </c:choose>
							    </c:forEach>
		                    </select>
						</td>
					</tr>
					<tr>
						<th>年份：</th>
						<td>
							<select id="qyear" name="question.qyear" >
								<c:forEach items="${yearList}" var="year" varStatus="status">
									<c:choose>
									<c:when test="${year == question.qyear}">
										<option value="${year}" selected="selected">${year}</option>
								    </c:when>
								    <c:otherwise>
										<option value="${year}">${year}</option>
								    </c:otherwise>
								    </c:choose>
							    </c:forEach>
	                    	</select>
						</td>
					</tr>
					<tr>
						<th>属性：</th>
						<td>
							<select id="qattribute" name="question.qattribute" class="middle validate[required]">
		                    	<c:forEach items="${attributeMap}" var="type" varStatus="status">
									<c:choose>
									<c:when test="${type.key == question.qattribute}">
										<option value="${type.key}" selected="selected">${type.value}</option>
								    </c:when>
								    <c:otherwise>
										<option value="${type.key}">${type.value}</option>
								    </c:otherwise>
								    </c:choose>
							    </c:forEach>
	                    	</select>
						</td>
					</tr>
					<tr>
						<th>难度：</th>
						<td>
							<select id="qdifficulty" name="question.qdifficulty" class="middle validate[required]">
		                    	<option value="" >-请选择-</option>
						        <c:forEach items="${difficultyMap}" var="type" varStatus="status">
									<c:choose>
									<c:when test="${type.key == question.qdifficulty}">
										<option value="${type.key}" selected="selected">${type.value}</option>
								    </c:when>
								    <c:otherwise>
										<option value="${type.key}">${type.value}</option>
								    </c:otherwise>
								    </c:choose>
							    </c:forEach>
	                    	</select>
						</td>
					</tr>
					<tr>
						<th>考试分类：</th>
						<td>
							<select name="question.qcategory" id="qcategory" multiple>
		                    	
						        <c:forEach items="${questionCatetoryMap}" var="type" varStatus="status">
									<option value="${type.key}">${type.value}</option>
							    </c:forEach>
		                    </select>
						</td>
					</tr>
					<!-- <tr>
						<th >批次号：</th>
						<td> -->
						<input type="hidden" id="qbatchnum" value="1" />
						<!-- </td>
					</tr> -->
					<tr>
						<th>知识点：</th>
						<td>
							<input name="qpointnames" id="qpointnames" type="text" onclick="funPopupCheckboxTree(this,'${qpointids}');" readonly="readonly" value="${qpointnames }" class="normal validate[required]" />
							<input name="qpointids" id="qpointids" type="hidden" value="${qpointids }"  />
						</td>
					</tr>
					<tr>
						<th >题干：</th>
						<td>
							<textarea name="question.qcontent" id="qcontent" >${question.qcontent }</textarea>
						</td>
					</tr>
					<tr>
						<th style="vertical-align:top;">子题集合 &nbsp;[<a href="javascript:void(0);" onclick="addchildQuestion();" style="color: red;">添加</a>]：</th>
						<td id="zishitiDiv">
							<!-- 以下样式部分用作定位  勿删除 ************************ -->
							<c:forEach items="${question.qchild}" var="child" varStatus="status">
								<table class="istablestyle">
									<tr>
										 <td></td>
										 <td class="childTitle">问题
											[<a href="###" onclick="updatechildq(this);" style="color: #027F9D;">修改</a>
											<a href="###" onclick="deletechildq(this);" style="color: red;">删除</a>]
										 </td>
									<tr>
										<tr>
											<th>子题干：</th>
											<td class="ztgca">${child.qccontent}</td>
										</tr>
										<c:forEach items="${child.qcchoiceList}" var="choice" varStatus="status">
											<tr>
												<th>
												<script type="text/javascript">
													var num = ${status.count};
													var option = String.fromCharCode(num+64);
													document.write("选项"+option);
												</script>
												</th>
												<td class="xcca">${choice}</td>
											</tr>
								    	</c:forEach>
								    	<tr>
											<th >答案：</th>
											<td class="daca">${child.qcans}</td>
										</tr>
										<tr>
											<th >解析：</th>
											<td class="jxca">${child.qccomment}</td>
										</tr>
										<tr>
											<th >拓展：</th>
											<td class="tzca">${child.qcextension}</td>
										</tr>
										<tr>
											<th >分值：</th>
											<td class="fzca">${child.qcscore}</td>
										</tr>
								</table>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th>其他 ：</th>
						<td>
							<ul class="ultab" id="othertab" style="width: 60%">
								<li class="active"><a href="#qparaphrase">释义</a></li>
								<li class=""><a href="#qfrom">信息源</a></li>
								<li class=""><a href="#qauthor">作者</a></li>
								<li class=""><a href="#qdiscussion">讨论</a></li>
								<li class=""><a href="#qskill">技巧</a></li>
								<li class=""><a href="#qvideourl">视频</a></li>
							</ul>
						</td>
					</tr>
					<tr>
						<th></th>
						<td id="othertabPoint" >
							<input class="normal" value="${question.qparaphrase }" id="qparaphrase" type="text" style="width: 250px"  value="" />
							<input class="tab-active" value="${question.qfrom }" id="qfrom" type="text" style="width: 250px"  value="" />
							<input class="tab-active" value="${question.qauthor }" id="qauthor" type="text" style="width: 250px"  value="" />
							<input class="tab-active" value="${question.qdiscussion }" id="qdiscussion" type="text" style="width: 250px"  value="" />
							<input class="tab-active" value="${question.qskill }" id="qskill" type="text" style="width: 250px"  value="" />
							<input class="tab-active" value="${question.qvideourl }" id="qvideourl" type="text" style="width: 250px"  value="" />
						</td>
					</tr>
					<tr>
						<th >审核意见：</th>
						<td>
						<textarea id="auditopinion" name="question.qauditopinion">
                            ${question.qauditopinion }
                        </textarea>
						</td>
					</tr>
					<tr >
						<th></th>
						<td>
							<input type="button" class="submit" id="unpassExamine" value="不通过"></input>
							<input type="button" class="submit" id="examine" value="审核通过"></input>
						</td>
					</tr>
					<tr style="height: 50px;"></tr>
				</table>
			</form>
		</div>
	</div>
	<!-- 弹出知识点选择树 -->
	<jsp:include page="../common/popupTree.jsp" />
	
<script type="text/javascript">
	//序列化对象
	function serializeObjJson(){
	    var JsonStr ="{"; 
	    var qid = $("#qid").val()!="" ? $("#qid").val() : ""; //主键Id
		JsonStr+="'qid':'" + qid + "',";
	    
		var qarea = $("#qarea").val()!="" ? $("#qarea").val() : ""; //地区
		JsonStr+="'qarea':'" + qarea + "',";
		/*分类*/
	 	var qcategory = $("#qcategory").val();
	 	var caas = qcategory.join(",");    //这是字符串
	 	var cas = caas.split(",");     //这是数组
	 	var str= "";
	 	for(var i=0;i<cas.length;i++){
	 		str = str + cas[i]+"','";
	 	}
	 	str = str.substring(0,str.length-2);
	 	str = "['"+str+"]"; 	
	 	JsonStr+="'qcategory':" + str + ",";
	 	/*分类end*/
		
		var qyear = $("#qyear").val()!="" ? $("#qyear").val() : ""; //年份
		JsonStr+="'qyear':'" + qyear + "',";
		
		var qattribute = $("#qattribute").val()!="" ? $("#qattribute").val() : ""; //是否真题
		JsonStr+="'qattribute':'" + qattribute + "',";
		
		var qdifficulty = $("#qdifficulty").val()!="" ? $("#qdifficulty").val() : ""; //难度
		JsonStr+="'qdifficulty':'" + qdifficulty + "',";
		
		var qpoint = $("#qpointids").val()!="" ? $("#qpointids").val() : ""; //知识点
        var end = qpoint.substring(qpoint.length-1);
		
		if(end != ','){
			qpoint = qpoint+",";
		}
		var pointarr = qpoint.split(',');/*知识点数组*/
		if(pointarr!="" && pointarr.length>0){
			JsonStr+="'qpoint':[";
			for(var j=0;j<pointarr.length-1;j++){
				if(j == pointarr.length-2){
					JsonStr+="'"+pointarr[j]+"'";
				}else{
					JsonStr+="'"+pointarr[j]+"',";
				}
			}
			JsonStr+="],";
		}
		var qcontent = CKEDITOR.instances['qcontent'].getData();//题干
		JsonStr+="'qcontent':'" + qcontent + "',";
		
		/***********************试题子集拼接 begin********************/
			JsonStr+="'qchild':[";//开始
			var tableHtmlObj = $("#zishitiDiv").find("table[class='istablestyle']");
			for(var m=0;m<tableHtmlObj.length;m++){
				 JsonStr+="{";//子试题
				 var xuanxiang ="'qcchoiceList':[";  //选项组装---------------
				 //整理需要修改的数据
				 var tableData = $(tableHtmlObj[m]).find("tr");
				 for(var i=0;i<tableData.length;i++){
					 var cells =  $(tableData[i]).children();
					 for(var j=0;j<cells.length;j++){
						  //获取td标签的class 和分别代表参数
						  var css =  $(cells[j]).attr("class");
						  var content = cells[j].innerHTML;
						  if(css =="ztgca"){//子题干
							  JsonStr+="'qccontent':'" + content + "',";
						  }else if(css =="daca"){//答案
							  JsonStr+="'qcans':'" + content + "',";
						  }else if(css =="jxca"){//解析
							  JsonStr+="'qccomment':'" + content + "',";
						  }else if(css =="tzca"){//拓展
							  JsonStr+="'qcextension':'" + content + "',";
						  }else if(css =="fzca"){//分值
							  JsonStr+="'qcscore':'" + content + "',";
						  }else if(css =="xcca"){//选项
							  xuanxiang+="'"+content+"',";
						  }
					 }
					 
				 }
				 alert(xuanxiang);
				 xuanxiang = xuanxiang.substring(0,xuanxiang.length-1);
				 xuanxiang+="]";
				 JsonStr+=xuanxiang;
				 if(m == tableHtmlObj.length-1){
					 JsonStr+="}"; 
				 }else{
					 JsonStr+="},"; 
				 }
			}
			JsonStr+="],";
		/***********************试题子集拼接  end********************/
		var qparaphrase = $("#qparaphrase").val()!="" ? $("#qparaphrase").val() : "";//释义
		JsonStr+="'qparaphrase':'" + qparaphrase + "',";
		
		var qfrom = $("#qfrom").val()!="" ? $("#qfrom").val() : "";//信息源
		JsonStr+="'qfrom':'" + qfrom + "',";
		
		var qauthor = $("#qauthor").val()!="" ? $("#qauthor").val() : "";//作者
		JsonStr+="'qauthor':'" + qauthor + "',";
		
		var qdiscussion = $("#qdiscussion").val()!="" ? $("#qdiscussion").val() : "";//讨论
		JsonStr+="'qdiscussion':'" + qdiscussion + "',";
		
		var qskill = $("#qskill").val()!="" ? $("#qskill").val() : "";//技巧
		JsonStr+="'qskill':'" + qskill + "',";
		var qbatchnum = $("#qbatchnum").val()!="" ? $("#qbatchnum").val() : "";//批次号
		JsonStr+="'qbatchnum':'" + qbatchnum + "',";
		
		var auditopinion = CKEDITOR.instances['auditopinion'].getData();//审核意见
		JsonStr+="'qauditopinion':'" + auditopinion + "',";
		
		var qvideourl = $("#qvideourl").val()!="" ? $("#qvideourl").val() : "";//视频
		JsonStr+="'qvideourl':'" + qvideourl + "',";
		
	    var qtype = "2";//类型 { 0 单选 、1多选、2 共用题干、 3共用选项}
		JsonStr+="'qtype':'" +qtype+ "'";
		JsonStr+="}";
	    
	    return 	JsonStr;	
 	}
</script>
