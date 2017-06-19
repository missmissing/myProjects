<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 修改试卷-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${TITLE }</title>
<%@ include file="/common/common.jsp"%>
<script type="text/javascript"
	src="<c:url value='/web-resource/scripts/jquery.multiselect/jquery.ui.core.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/web-resource/scripts/jquery.multiselect/jquery.ui.widget.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/web-resource/scripts/jquery.multiselect/jquery.multiselect.js' />"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/web-resource/styles/jquery.multiselect/jquery.multiselect.css' />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/web-resource/styles/jquery.multiselect/jquery-ui.css' />" />

<script type="text/javascript">
	$(function() {
		
		var pareas = '${paper.pareas }';
		 var objSelect = $("#pareas").get(0);
		  for (var i = 0; i < objSelect.options.length; i++) {  
			  var str = $("#pareas").get(0).options[i].value;
			  if(pareas.indexOf(str) != -1){
				  $("#pareas").get(0).options[i].selected = true;
			  }             
		  }
		  
		  var pcategorys = '${paper.pcategorys }';
			 var objSelect = $("#pcategorys").get(0);
			  for (var i = 0; i < objSelect.options.length; i++) {  
				  var str2 = $("#pcategorys").get(0).options[i].value;
				  if(pcategorys.indexOf(str2) != -1){
					  $("#pcategorys").get(0).options[i].selected = true;
				  }             
			  }
			  
			  
	      var porgs = '${paper.porgs }';
		  var objSelect = $("#porgs").get(0);
			  for (var i = 0; i < objSelect.options.length; i++) {  
				   var str3 = $("#porgs").get(0).options[i].value;
				   if(porgs.indexOf(str3) != -1){
					  $("#porgs").get(0).options[i].selected = true;
				  }             
			  }  
		  	  
		
		$("#pareas").multiselect({
			selectedPosition: 'right',
			noneSelectedText: "==请选择==",
	        checkAllText: "全选",
	        uncheckAllText: "全不选",
	        selectedList: 100
	        
		});
		
		$("#pcategorys").multiselect({
			selectedPosition: 'right',
			noneSelectedText: "==请选择==",
	        checkAllText: "全选",
	        uncheckAllText: "全不选",
	        selectedList: 100
	        
		});
		$("#porgs").multiselect({
			selectedPosition: 'right',
			noneSelectedText: "==请选择==",
	        checkAllText: "全选",
	        uncheckAllText: "全不选",
	        selectedList: 100
	        
		});
		
		
		
		validationEngine("paperForm");//绑定formId检验表单
		//修改试卷
		$("#update").bind("click", function() {
			if ($("#paperForm").validationEngine("validate")) {
				$.post("<c:url value='/paper/update.html' />",$("#paperForm").serialize(), function(response) {
					if (response.success) {
						if(confirm("修改试卷成功！"))
						{
						}
						var url = "<c:url value='/paper/list.html' />";
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
			var url = "<c:url value='/paper/list.html' />";
			parent.openIframePage("",url);
		});
	});
</script>
<div class="headbar">
	<div class="operating" style="overflow: visible">
		<a class="hack_ie" href="###" id="returnList">
			<button class="operating_btn" type="button">
				<span>返回列表</span>
			</button>
		</a>
	</div>
</div>
<div class="content_box">
	<div class="content form_content"
		style="height: 420px; overflow-y: scroll;">
		<form id="paperForm" method="post" accept-charset="utf-8"
			enctype="multipart/form-data">
			<input id="pid" type="hidden" name="paper.pid" value="${paper.pid }" />
			<div style="display: none">
				<input type="hidden" name="dilicms_csrf_token" value="" />
			</div>
			<table class="form_table">
				<col width="150px" />
				<col />
				<tr>
					<th>试卷标题：</th>
					<td><input name="paper.ptitle" value="${paper.ptitle }"
						id="ptitle" type="text" class="normal validate[required]" /></td>
				</tr>
				<tr>
					<th>副标题：</th>
					<td><input name="paper.psubtitle" value="${paper.psubtitle }"
						id="psubtitle" type="text" class="normal validate[required]" /></td>
				</tr>
				<tr>
					<th>试卷区域：</th>
					<td><select name="paper.pareas" id="pareas" multiple>

							<c:forEach items="${areaMap}" var="type" varStatus="status">
								<option value="${type.key}">${type.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>试卷分类：</th>
					<td><select name="paper.pcategorys" id="pcategorys" multiple>

							<c:forEach items="${category_Y_Enum}" var="type"
								varStatus="status">
								<option value="${type.key}">${type.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>试卷时限(分钟)：</th>
					<td>
				        <input name="paper.ptimelimit" id="ptimelimit" type="text" value="${paper.ptimelimit}" class="normal validate[required,custom[integer]]" />
						
					</td>
				</tr>
				<tr>
					<th>所属分校：</th>
					<td><select name="paper.porgs" id="porgs" multiple>
							<c:forEach items="${orglist}" var="type" varStatus="status">
								<option value="${type.organizationNo}">${type.name}</option>
							</c:forEach>
					</select></td>
				</tr>
				<!--  
				<tr>
					<th>地区：</th>
					<td><select name="paper.parea" id="parea"
						class="middle validate[required]">
							<option value="">-请选择-</option>
							<c:forEach items="${areaMap}" var="type" varStatus="status">
								<c:choose>
									<c:when test="${type.key == paper.parea}">
										<option value="${type.key}" selected="selected">${type.value}</option>
									</c:when>
									<c:otherwise>
										<option value="${type.key}">${type.value}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</select></td>
				</tr>
				-->
				<tr>
					<th>年份：</th>
					<td><select name="paper.pyear" id="pyear"
						class="middle validate[required]">
							<option value="">-请选择-</option>
							<c:forEach items="${yearList}" var="val" varStatus="status">
								<c:choose>
									<c:when test="${val == paper.pyear}">
										<option value="${val}" selected="selected">${val}</option>
									</c:when>
									<c:otherwise>
										<option value="${val}">${val}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>属性：</th>
					<td><select name="paper.pattribute" id="pattribute"
						class="middle validate[required]">
							<option value="">-请选择-</option>
							<c:forEach items="${attributeMap}" var="type" varStatus="status">
								<c:choose>
									<c:when test="${type.key == paper.pattribute}">
										<option value="${type.key}" selected="selected">${type.value}</option>
									</c:when>
									<c:otherwise>
										<option value="${type.key}">${type.value}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>扩展信息 ：</th>
					<td>
						<ul class="ultab" id="othertab" style="width: 60%">
							<li class="active"><a href="#qfrom">信息源</a></li>
							<li class=""><a href="#qdiscussion">讨论</a></li>
							<li class=""><a href="#qskill">技巧</a></li>
							<li class=""><a href="#qvideourl">视频</a></li>
						</ul>
					</td>
				</tr>
				<tr>
					<th></th>
					<td id="othertabPoint"><input class="normal" id="qfrom"
						type="text" style="width: 250px" value="${paper.pattrs.qfrom}" name="qfrom" /> <input
						class="tab-active" id="qdiscussion" type="text"
						style="width: 250px" value="${paper.pattrs.qdiscussion}"  name="qdiscussion"/> <input class="tab-active"
						id="qskill" type="text" style="width: 250px" value="${paper.pattrs.qskill}" name="qskill"/> <input
						class="tab-active" id="qvideourl" type="text" style="width: 250px"
						value="${paper.pattrs.qvideourl}"  name="qvideourl"/></td>
				</tr>
				<tr>
					<th></th>
					<td><input type="button" class="submit" id="update" value="修改"></input>
					</td>
				</tr>
				<tr style="height: 50px;"></tr>
			</table>
		</form>
	</div>
</div>
