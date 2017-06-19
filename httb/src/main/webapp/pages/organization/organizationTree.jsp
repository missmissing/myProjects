<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/common.jsp"%>
<!-- dhtmlx.popup -->
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxcommon.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxpopup.js' />"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxpopup_dhx_terrace.css' />" media="all" />


<script type="text/javascript">

var setting = {
	view: {
		dblClickExpand: false
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeClick: beforeClick,
		onClick: onClick
	}
};

function beforeClick(treeId, treeNode) {
	var check = (treeNode && !treeNode.isParent);
	if (!check) alert("只能选择城市...");
	return check;
}

function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	nodes = zTree.getSelectedNodes(),
	v = "";
	nodes.sort(function compare(a,b){return a.id-b.id;});
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	$("#orgname").attr("value", v);
	v = "";
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].id + ",";
	}
	if (v.length > 0 ) 
		v = v.substring(0, v.length-1);
	$("#orgid").attr("value", v);
}

function loadOrgTree() {
	$.ajax({
		url : "<c:url value='/user/getOrgnizationTree.html' />",
		data : null,
		dataType : 'json',
		type : 'POST',
		cache : false,
		success : function(response) {
			if (response.success) {
				//funCreateTree_ht(true,"treeDemo",response.data,null,onTreeCheckFun,beforeCheck);
				viewTree(response.data);
			}else{
				$("#treeDemo").html(response.message);
			}
		}, 
		error : function(response) {
			$("#treeDemo").html("服务器错误,请联系管理员");
		}
	});
}

function viewTree(treedata){
	var e=document.getElementById("orgname");
    var t=e.offsetTop;  
    var l=e.offsetLeft;  
    var height=e.offsetHeight;  
    while(e=e.offsetParent) {  
        t+=e.offsetTop;  
        l+=e.offsetLeft;  
    }  
	$("#menuContent").css({left:l+160+ "px", top:t+ "px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
	$.fn.zTree.init($("#treeDemo"), setting, treedata);
}

function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}

function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}

$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	 $("#treeDemo").css("background","#FFF0F5")
});
</script>

<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:260px;height:200px;max-height: 300px;overflow: auto; background-color:#FAFAFA "></ul>
</div>
