<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<title>${TITLE }</title>
<script type="text/javascript"
	src="<c:url value='/web-resource/scripts/jquery/jquery-1.7.2.min.js' />"></script>
<link rel="SHORTCUT ICON"
	href="<c:url value='/web-resource/styles/common/images/ico.ico' />"
	media="all" />
<link rel="Bookmark"
	href="<c:url value='/web-resource/styles/common/images/ico.ico' />"
	media="all" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/web-resource/styles/theme/content.css' />" />
<head>
<!-- 引入公共页面 -->

<%@ include file="/common/common.jsp"%>

<script type="text/javascript">
	/**进入到角色添加页*/
	function goback() {
		var url = "<c:url value='/menu/list.html' />";
		window.parent.document.getElementById("content_ifr").src = url;
	}
	
	function save() {
		if($("#name").val()==""||$("#level").val()==""||$("#url").val()==""||$("#parentName").val()==""||$("#orderNum").val()==""){
			alert("您有未输入的必填项！");
			return false;
		}
		
		var reg = new RegExp("^[0-9]+$"); 
		if(!reg.test($("#orderNum").val())){  
		        alert("排序字段必须为正整数数字!");
		        return false;
		}  
		
		$.ajax({
			url : "<c:url value='/menu/save.action' />",
			data : $("#addForm").serialize(),
			dataType : 'json',
			type : 'POST',
			cache : false,
			success : function(data) {
				if (data.success) {
					location.href="<c:url value='/menu/list.html'/>";
				} else {
					var meg = data.message;
					if(typeof(meg) =='undefined'){
						alert("添加角色信息错误，请联系管理员！");
					}else{
						alert(data.message);
					}
					return false;
				}
			}, 
			error : function(data) {
				var meg = data.message;
				if(typeof(meg) =='undefied'){
					alert("添加角色信息错误，请联系管理员！");
				}else{
					alert(data.message);
				}
				return false;
			},
			async:false
		});
	}
	  <!--
	    var setting = {
	  	      check: {
	  	        enable: true,
	  	        chkStyle: "checkbox",
	  	        chkboxType: { "Y": "s", "N": "ps" }
	  	      },
	  	      data: {
	  	        simpleData: {
	  	          enable: true
	  	        }
	  	      }, callback: {
	  	    	onCheck: zTreeOnCheck
	          }          
	  	      
	  	    };
	    
	    $(document).ready(function(){
	    	loadMenus();
	    });
	    
	    function painTree(treeData){
	    	$.fn.zTree.init($("#treeDemo"),setting,treeData);
	    }
	    
		function loadMenus(){
			var role_id='tree';
			$.ajax({
				url : "<c:url value='/menu/getAllMenu'/>",
				data : "roleId="+role_id,
				dataType : 'json',
				type : 'POST',
				cache : false,
				success : function(data) { 
					if (data.success) {
						painTree(data.data);
					} else {
						var meg = data.message;
						if(typeof(meg) =='undefined'){
							alert("加载菜单信息错误，请联系管理员！");
						}else{
							alert(data.message);
						}
						return false;
					}
				}, 
				error : function(data) {
					var meg = data.message;
					if(typeof(meg) =='undefied'){
						alert("加载菜单信息错误，请联系管理员！");
					}else{
						alert(data.message);
					}
					return false;
				},
				async:false
			});
		} 
		
		function zTreeOnCheck(event, treeId, treeNode) {
	        if (treeNode) {
	            var parentId = $("#parentId");
	            parentId.attr("value", treeNode.id);
	            var parentName = $("#parentName");
	            parentName.attr("value", treeNode.name);
	        }
	    }
		
		function showTreeDemo(){
			$("#treeDemoDIV").show();
		}
		
		
	    //-->

	
</script>
</head>
<body>
	<div class="headbar">
		<div class="operating" style="overflow: visible">
			<a class="hack_ie" href="javascript:void(0);"><button
					class="operating_btn" onclick="goback();" type="button">
					<span>返回列表</span>
				</button></a>
		</div>
	</div>
	<div class="content_box">
		<div class="content form_content">
		<table style="width: 100%">
		<tr>
		<td style="width: 20%">
		<form id="addForm" name="addForm">
				<table class="form_table">
					<col width="150px" />
					<col />
					<input type="hidden" class="normal" id="id" name="id"
						value="${menu.id}" />
					<tr>
						<th>菜单名称：</th>
						<td width="250"><input type="text" class="normal" id="name"
						name="name" style="width: 150px" value="${menu.name}" />
						<span style="color: #FF0000" >*</span>
						</td>
					</tr>
					<tr>
						<th>菜单级别：</th>
						<td width="250"><input type="text" class="normal" id="level"
							name="level" style="width: 150px" value="${menu.level}" />
							<span style="color: #FF0000" >*</span>
							</td>
					</tr>
					<tr>
						<th>菜单URL：</th>
						<td width="250"><input type="text" class="normal" id="url"
							name="url" style="width: 150px" value="${menu.url}" />
							<span style="color: #FF0000" >*</span>
							</td>
					</tr>
					<tr>
						<th>上级菜单：</th>
						<td><input type="textarea" class="normal" id="parentName" name="parentName" 
						onclick="showTreeDemo();"
						style="width: 150px" value="${menu.parentName}" readonly="readonly"/>
						<input type="hidden" class="normal" id="parentId" name="parentId" 
						style="width: 150px" value="${menu.parentId}" readonly="readonly"/>
						<span style="color: #FF0000" >*</span>
						</td>
						
					</tr>
					
					<tr>
						<th width="250">排序大小：</th>
						<td><input type="textarea" class="normal" id="orderNum"
							name="orderNum" style="width: 150px"
							value="${menu.orderNum}" /><span style="color: #FF0000" >*</span>
							</td>
					</tr>
					
					<tr>
						<th>备注信息：</th>
						<td><input type="textarea" class="normal" id="description"
							name="description" style="width: 150px"
							value="${menu.description}" />
							</td>
					</tr>
					<tr>
						<th></th>
						<td colspan="2"><input type="button" id="savebtn"
							name="savebtn" value="保  存" onclick="save();" /> <input
							type="button" class="canclebtn" value="取  消" onclick="goback();" />
						</td>
					</tr>
				</table>
			</form>
        </td>
        <td>
        <div id="treeDemoDIV" class="zTreeDemoBackground left" style="display: none;">
            <ul id="treeDemo" class="ztree" style="height: 200px; width:400px; overflow-y: scroll "></ul>
        </div>
        </td>
		</tr>
		</table>
			
		</div>
	</div>
	
						    
</body>
</html>
