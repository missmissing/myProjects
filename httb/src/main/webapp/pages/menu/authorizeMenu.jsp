<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<!-- 引入公共页面 -->
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/common.jsp"%>
<html>
<link rel="SHORTCUT ICON" href="<c:url value='/web-resource/styles/common/images/ico.ico' />" media="all" />
<link rel="Bookmark" href="<c:url value='/web-resource/styles/common/images/ico.ico' />" media="all" />
<link rel="stylesheet" type="text/css" href="<c:url value='/web-resource/styles/theme/content.css' />" />
<head>
  <title>菜单树</title>
  <script type="text/javascript">
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
  	      }
  	    };
    
    $(document).ready(function(){
    	loadMenus();
    });
    
    function painTree(treeData){
    	$.fn.zTree.init($("#treeMultiple"),setting,treeData);
    }
    
	function loadMenus(){
		var role_id=$("#role_id").val();
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
	
	function goback() {
		var url = "<c:url value='/role/list.html' />";
		//window.parent.document.getElementById("content_ifr").src = url;
		parent.openIframePage("",url);
	} 	
	
    function save(){
        var treeObj=$.fn.zTree.getZTreeObj("treeMultiple");
        var nodes=treeObj.getCheckedNodes(true);
        var menuIds="";
        for(var i=0;i<nodes.length;i++){
        	menuIds+=nodes[i].id + ",";
        }
        
        var role_id=$("#role_id").val();
        $.ajax({                    
    		url : "<c:url value='/role/save_role_menu.action'/>",
    		data : "roleId="+role_id+"&menuIds="+menuIds,
    		dataType : 'json',
    		type : 'POST',
    		cache : false,
    		success : function(data) {
    			if (data.success) {
    				location.href="<c:url value='/role/list.html'/>";
    			} else {
    				alert("配置用户角色错误，请联系管理员！");
    				return false;
    			}
    		}, 
    		error : function(data) {
    			alert("配置用户角色错误，请联系管理员！");
    			return false;
    		},
    		async:false
    	});
     }
    
    //-->
  </script>
</head>

<body>
<div class="content_wrap" style="text-align: center;">
<input type="hidden" id="role_id" name="role_id" value="${requestScope.role_id}" /> 
  <div class="zTreeDemoBackground left">
    <ul id="treeMultiple" class="ztree" style="height: 300px; width:400px; overflow-y: auto"></ul>
  </div>
  <table>
	<tr>
		<td colspan="2">
			<input type="button" id="savebtn"  name="savebtn" value="保  存"  onclick="save();"/>
			<input type="button" class="canclebtn" value="取  消"   onclick="goback();"/>
		</td>
	</tr>
</table>
</div>
</body>
</html>
