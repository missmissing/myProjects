<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 添加标准选择题 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${TITLE }</title>
<%@ include file="/common/common.jsp"%>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery.multiselect/jquery.ui.core.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery.multiselect/jquery.ui.widget.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery.multiselect/jquery.multiselect.js' />"></script>
<link   rel="stylesheet" type="text/css"   href="<c:url value='/web-resource/styles/jquery.multiselect/jquery.multiselect.css' />" />
<link   rel="stylesheet" type="text/css"   href="<c:url value='/web-resource/styles/jquery.multiselect/jquery-ui.css' />" />

<script type="text/javascript">
	var character = new Array("A","B","C","D","E","F","G","H","I","J");//允许这些选项
	$(function() {
		
		$("#qcategory").multiselect({
			selectedPosition: 'right',
			noneSelectedText: "==请选择==",
	        checkAllText: "全选",
	        uncheckAllText: "全不选",
	        selectedList: 100
	        
		});
		
		validationEngine("questionForm");//绑定formId检验表单
		funYearList_ht("qyear");//初始化年份列表
		//保存试题
		$("#save").bind("click", function() {
			//var valuestr = $("#qcategory").multiselect("getChecked").map(function(){return this.value; }).get();
			var valuestr = $("#qcategory").val();
			if(null==valuestr||''==valuestr )
			{
				alert("考试分类不能为空");
				return;
			}
			
			var qcontent = CKEDITOR.instances['qcontent'].getData();//子题干
			if(qcontent == "" || qcontent.length<=0){
				alert("题干不能为空");
				return;
			}
			
			var choices = $("#questionForm").find("span[class='badge']");//选项编号
			var number = choices.length; //选项个数
			if(character[number-1] !=choices[number-1].title ){
				alert("选项顺序错误 请按ABC...排列");
				return;
			}
			var options = $("#questionForm").find("textarea[class='isnostyle']");//选项集合
			for(var i=0;i<options.length;i++){
				var opeint = CKEDITOR.instances[options[i].id].getData();
				if(opeint == "" || opeint.length<=0){
					alert("选择"+options[i].id+"不能为空");
					return;
				}
			}
			
			if ($("#questionForm").validationEngine("validate")) {
				$.post("<c:url value='/question/save.html' />",{ jsonStr:serializeObjJson()}, function(response) {
					if (response.success) {
						if(confirm("试题添加成功！"))
						{
						}
						parent.minWins();
						var url = "<c:url value='/question/list.html' />";
						parent.openIframePage("",url);
					}else{
						alert(response.message);
					}

				}, "json");
			}
			
		});
		
		//examine提交审核
		$("#examine").bind("click", function() {
			//var valuestr = $("#qcategory").multiselect("getChecked").map(function(){return this.value; }).get();
			var valuestr = $("#qcategory").val();
			if(null==valuestr||''==valuestr )
			{
				alert("考试分类不能为空");
				return;
			}
			
			var qcontent = CKEDITOR.instances['qcontent'].getData();//子题干
			if(qcontent == "" || qcontent.length<=0){
				alert("题干不能为空");
				return;
			}
			
			var choices = $("#questionForm").find("span[class='badge']");//选项编号
			var number = choices.length; //选项个数
			if(character[number-1] !=choices[number-1].title ){
				alert("选项顺序错误 请按ABC...排列");
				return;
			}
			var options = $("#questionForm").find("textarea[class='isnostyle']");//选项集合
			for(var i=0;i<options.length;i++){
				var opeint = CKEDITOR.instances[options[i].id].getData();
				if(opeint == "" || opeint.length<=0){
					alert("选择"+options[i].id+"不能为空");
					return;
				}
			}
			
			if ($("#questionForm").validationEngine("validate")) {
				$.post("<c:url value='/question/examine.html' />",{ jsonStr:serializeObjJson()}, function(response) {
					if (response.success) {
						if(confirm("提交审核成功！"))
						{
						}
						parent.minWins();
						var url = "<c:url value='/question/list.html' />";
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
	/**进入到试题添加页*/
	function openAddPage() {
		var url = "<c:url value='/question/list.html' />";
		window.parent.document.getElementById("content_ifr").src = url;
	}
	/**添加新选项*/
	function addOption(){
		var newXuanXiang = "";//新选项标识
		var canshu =new Array();//已经有的参数数组
		var character = new Array("A","B","C","D","E","F","G","H","I","J");//允许这些选项
		var choices = $("#questionForm").find("span[class='badge']");
		var number =4; //默认四个选项
		var max = 4;//最大选项的下标
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
						"<textarea class='isnostyle' onclick='createEditor(this);'  id='"+newXuanXiang+"'></textarea></br></ht>";
		$("#optionDiv").append(optionDiv);
		var optionAnswer = "<label><input name='answer' type='checkbox' value='"+newXuanXiang+"' onclick='qcansValue(this);' />"+newXuanXiang+"</label>";
		$("#optionAnswer").append(optionAnswer);
		createEditor(newXuanXiang);//初始化 富文本
		
	}
	function deleteOption(inp){
		$(inp).parent().empty();
	}
	/**生成富文本编辑器*/
	function createEditor(inp){
		var uploadUrl = "<c:url value='/imagemanage/ckEditorUpload.html' />";
		createCkEditor_ht(inp,uploadUrl);
		
	}
	/**该页面 知识点选择树 回调函数 方法名和参数 固定不变
	 *  和include page="../common/popupTree.jsp 联合使用
	 */
	function onTreeCheckFun(e, treeId, treeNode) {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getNodesByFilter(filter); // 查找叶子节点选择节点集合
		var nodeids ="";
		var nodenames ="";
		for(var i=0;i<nodes.length;i++){
			nodenames+=nodes[i].name+",";
			nodeids+=nodes[i].id+",";
		}
		$("#qpointnames").val(nodenames);
		$("#qpointids").val(nodeids);
		
		//手动失去焦点，触发非空验证
		$("#qpointnames").blur();
	}
	//页面加载完成后初始化 富文本编辑器 
	window.onload  = function(){ //children    
		var textareas = $("#questionForm").find("textarea");
		for(var i=0;i<textareas.length;i++){
			createEditor(textareas[i]);
		}
	}
	
	function qcansValue(a){
		var qcansvalue = $("#qcans").val();
		var index = qcansvalue.indexOf(a.value)
		if(a.checked){
			if(index <= 0){
				qcansvalue+= a.value;
			}
		}
		else{
            if(index >= 0){
            	qcansvalue = qcansvalue.substring(0,index) + qcansvalue.substring(index+1);
			}           
		}
		var b=qcansvalue.split(""); 
		b.sort();
	    var c=b.join(""); 
		$("#qcans").val(c);
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
				<div style="display: none">
					<input type="hidden" name="dilicms_csrf_token" value="" />
				</div>
				<table class="form_table">
				     <col width="150px" />
					<col />
					<tr>
						<th>地区：</th>
						<td>
							<select id="qarea" name="question.qarea" >
		                    	<option value="" >-请选择-</option>
						        <c:forEach items="${areaMap}" var="type" varStatus="status">
									<option value="${type.key}">${type.value}</option>
							    </c:forEach>
		                    </select>
						</td>
					</tr>
					<tr>
						<th>年份：</th>
						<td>
							<select id="qyear" name="question.qyear" >
	                    	</select>
						</td>
					</tr>
					<tr>
						<th>属性：</th>
						<td>
							<select id="qattribute" name="question.qattribute" class="middle validate[required]">
		                    	<option value="" >-请选择-</option>
						        <c:forEach items="${attributeMap}" var="type" varStatus="status">
									<option value="${type.key}">${type.value}</option>
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
									<option value="${type.key}">${type.value}</option>
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
					<!-- <tr >
						<th >批次号：</th>
						<td> -->
							<input type="hidden" id="qbatchnum" value="1" />
					<!-- 	</td>
					</tr> -->
					<tr>
						<th>知识点：</th>
						<td>
							<input name="qpointnames" id="qpointnames" type="text" onclick="funPopupCheckboxTree(this,'');" readonly="readonly" value="${qpointnames }" class="normal validate[required]" />
							<input name="qpointids" id="qpointids" type="hidden" value="${qpointids }"  />
		
						</td>
					</tr>
					<tr>
						<th >题干：</th>
						<td>
							<textarea name="question.qcontent" id="qcontent"></textarea>
						</td>
					</tr>
					<tr>
						<th style="vertical-align:top;" >选项 &nbsp;[<a href="javascript:void(0);" onclick="addOption();" style="color: red;">添加</a>]：</th>
						<td id="optionDiv">
							<span class="badge" title="A">A</span><br/>
							<textarea class="isnostyle" id="A"></textarea><br/>
							<ht><span class="badge" title="B">B</span><br/>
							<textarea class="isnostyle" id="B"></textarea><br/>
							<ht>
								<span class="badge" title="C">C</span>
								<span class="optionDel" onclick="deleteOption(this);">删除</span>
								<br/>
								<textarea class="isnostyle" id="C"></textarea><br/>
							</ht>
							<ht>
								<span class="badge" title="D">D</span>
							    <span class="optionDel" onclick="deleteOption(this);">删除</span>
							    <br/>
							    <textarea class="isnostyle" id="D"></textarea><br/>
							</ht>
						</td>
					</tr>
					<tr>
						<th >答案：</th>
						<td id="optionAnswer">
							<input id="qcans"  type="text" value="" class="normal validate[required]"  readonly="readonly"/>
                            <label><input name="answer" type="checkbox" value="A" onclick="qcansValue(this);" />A</label> 
                            <label><input name="answer" type="checkbox" value="B" onclick="qcansValue(this);" />B</label> 
                            <label><input name="answer" type="checkbox" value="C" onclick="qcansValue(this);" />C</label> 
                            <label><input name="answer" type="checkbox" value="D" onclick="qcansValue(this);" />D</label> 
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
							<input id="qcscore" class="validate[required,custom[number]]" />
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
					<tr >
						<th></th>
						<td>
							<input type="button" class="submit" id="save" value="存为草稿"></input>
							<input type="button" class="submit" id="examine" value="提交审核"></input>
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
			JsonStr+="'qchild':[{";//开始
				var qcans = $("#qcans").val()!="" ? $("#qcans").val() : ""; //答案
				JsonStr+="'qcans':'" + qcans + "',";
				
				var qccomment = CKEDITOR.instances['qccomment'].getData();//解析
				JsonStr+="'qccomment':'" + qccomment + "',";
				
				var qcextension = CKEDITOR.instances['qcextension'].getData();//拓展
				JsonStr+="'qcextension':'" + qcextension + "',";
				
				var qcscore = $("#qcscore").val()!="" ? $("#qcscore").val() : ""; //分值
				JsonStr+="'qcscore':'" + qcscore + "',";
				
				var options = $("#questionForm").find("textarea[class='isnostyle']");//选项集合
				JsonStr+="'qcchoiceList':[";
				for(var i=0;i<options.length;i++){
					var opeint = CKEDITOR.instances[options[i].id].getData();
					if(i == options.length-1){
						JsonStr+="'"+opeint+"'";
					}else{
						JsonStr+="'"+opeint+"',";
					}
				}
				JsonStr+="]";
		JsonStr+="}],";
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
		
		var qvideourl = $("#qvideourl").val()!="" ? $("#qvideourl").val() : "";//视频
		JsonStr+="'qvideourl':'" + qvideourl + "',";
		
	    var qtype = qcans.length>1 ? "1":"0";//类型 { 单选 、多选、共用题干、共用选项}
		JsonStr+="'qtype':'" +qtype+ "'";
		JsonStr+="}";
	    
	    return 	JsonStr;	
 	}
</script>
