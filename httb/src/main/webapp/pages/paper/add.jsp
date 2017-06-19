<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 添加试卷-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${TITLE }</title>
<%@ include file="/common/common.jsp"%>
<!-- jquery.multiselect -->
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery.multiselect/jquery.ui.core.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery.multiselect/jquery.ui.widget.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery.multiselect/jquery.multiselect.js' />"></script>
<link   rel="stylesheet" type="text/css"   href="<c:url value='/web-resource/styles/jquery.multiselect/jquery.multiselect.css' />" />
<link   rel="stylesheet" type="text/css"   href="<c:url value='/web-resource/styles/jquery.multiselect/jquery-ui.css' />" />

<script type="text/javascript">
	$(function() {
		validationEngine("paperForm");//绑定formId检验表单
		
		
		$("#moduleIds").multiselect({
			selectedPosition: 'right',
			noneSelectedText: "==请选择==",
	        checkAllText: "全选",
	        uncheckAllText: "全不选",
	        selectedList: 100
	        
		});
		
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
		
		
		
		//保存试卷
		$("#save").bind("click", function() {
			var valuestr = $("#moduleIds").multiselect("getChecked").map(function(){return this.value; }).get();
			var pareasIds = $("#pareasIds").multiselect("getChecked").map(function(){return this.value; }).get();
			$("#paperpareas").val(pareasIds);
			
			if ($("#paperForm").validationEngine("validate")) {
				$.post("<c:url value='/paper/save.html' />",$("#paperForm").serialize(), function(response) {
					if (response.success) {
						if(confirm("添加试卷成功！"))
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
		   <button class="operating_btn" type="button" >
				<span>返回列表</span>
			</button>
		</a>
	</div>
</div>
	<div class="content_box">
		<div class="content form_content" style="height: 420px;overflow-y: scroll;">
			<form id="paperForm" method="post" accept-charset="utf-8" enctype="multipart/form-data">
				<div style="display: none">
					<input type="hidden" name="dilicms_csrf_token" value="" />
				</div>
				<table class="form_table">
				    <col width="150px" />
					<col />
					<tr>
						<th>试卷标题：</th>
						<td>
							<input name="paper.ptitle" id="ptitle" type="text" class="normal validate[required]" />
						</td>
					</tr>
					<tr>
						<th>副标题：</th>
						<td>
							<input name="paper.psubtitle" id="psubtitle" type="text" class="normal validate[required]" />
						</td>
					</tr>
					
					<tr>
						<th>试卷区域：</th>
						<td>
							<select name="paper.pareas" id="pareas" multiple>
		                    	
						        <c:forEach items="${areaMap}" var="type" varStatus="status">
									<option value="${type.key}">${type.value}</option>
							    </c:forEach>
		                    </select>
						</td>
					</tr>
					<tr>
						<th>试卷分类：</th>
						<td>
							<select name="paper.pcategorys" id="pcategorys" multiple>
		                    	
						        <c:forEach items="${category_Y_Enum}" var="type" varStatus="status">
									<option value="${type.key}">${type.value}</option>
							    </c:forEach>
		                    </select>
						</td>
					</tr>
					<tr>
						<th>试卷时限(分钟)：</th>
						<td>
					        <input name="paper.ptimelimit" id="ptimelimit" type="text" class="normal validate[required,custom[integer]]" />
							
						</td>
					</tr>
					<tr>
						<th>所属分校：</th>
						<td>
							<select name="paper.porgs" id="porgs" multiple>
						        <c:forEach items="${orglist}" var="type" varStatus="status">
									<option value="${type.organizationNo}">${type.name}</option>
							    </c:forEach>
		                    </select>
						</td>
					</tr>
					<tr>
						<th>年份：</th>
						<td>
							<select name="paper.pyear" id="pyear" class="middle validate[required]">
								<option value="" >-请选择-</option>
						         <c:forEach items="${yearList}" var="val" varStatus="status">
									<option value="${val}">${val}</option>
							    </c:forEach>
	                    	</select>
						</td>
					</tr>
					<tr>
						<th>属性：</th>
						<td>
							<select  name="paper.pattribute" id="pattribute" class="middle validate[required]">
		                    	<option value="" >-请选择-</option>
						        <c:forEach items="${attributeMap}" var="type" varStatus="status">
									<option value="${type.key}">${type.value}</option>
							    </c:forEach>
	                    	</select>
						</td>
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
						type="text" style="width: 250px" value="" name="qfrom" /> <input
						class="tab-active" id="qdiscussion" type="text"
						style="width: 250px" value=""  name="qdiscussion"/> <input class="tab-active"
						id="qskill" type="text" style="width: 250px" value="" name="qskill"/> <input
						class="tab-active" id="qvideourl" type="text" style="width: 250px"
						value=""  name="qvideourl"/></td>
					</tr>
					<tr >
						<th></th>
						<td>
							<input type="button" class="submit" id="save" value="添加"></input>
						</td>
					</tr>
					<tr style="height: 50px;"></tr>
				</table>
			</form>
		</div>
	</div>
