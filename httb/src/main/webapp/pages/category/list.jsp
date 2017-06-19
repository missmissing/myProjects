<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/common.jsp"%>
<script type="text/javascript">
 	//tree配置对象
	 var setting = {
			view: {
				addHoverDom: addHoverDom,   //鼠标经过时显示按钮
				removeHoverDom: removeHoverDom, //鼠标离离开时隐藏按钮
				selectedMulti: false ,//不支持选择多个节点
				fontCss: getFontCss
			},
			edit: {
				enable: true, //是否开启异步加载模式
				editNameSelectAll: true, //节点编辑名称 input 初次显示时,设置 txt 内容是否为全选状态
				removeTitle: "删除",  //重新定义删除按钮title
				renameTitle: "编辑"   //重新定义编辑按钮title
			},
			data: {
				simpleData: {
					enable: true //是否采用简单数据模式 (Array)
				}
			},
			callback: {
				beforeEditName: beforeEditName, //修改前触发函数
				//beforeRename: beforeRename,	//修改后的校验函数
				//onRename: onRename,	//修改
				beforeRemove: beforeRemove,	//删除前触发函数
				onRemove: onRemove	//删除
			}
	 };
 	 //节点样式变更
	 function getFontCss(treeId, treeNode) {
		return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
	 }
	//修改前触发
	function beforeEditName(treeId, treeNode) {
		var url = "<c:url value='/category/update.html' />";  //章节删除后台URL
		updateTreeNode_ht(url,"treeDemo",treeNode);
		return false;
	}
	//删除前触发
	function beforeRemove(treeId, treeNode) {
		var url = "<c:url value='/category/delete.html' />";  //章节删除后台URL
		art.dialog({
			width: '20em',
			top: '10%',
		    title: '提示',
		    content: '确定删除【'+treeNode.name+'】节点',
		    ok: function () {
		    	var isParent = treeNode.isParent;
		    	//以下判断是否有子集合 没有直接删除  反之不能删除
				if(!isParent){
					deleteTreeNode_ht(url,"treeDemo",treeNode);
				}else{
					alert("该节点下有子节点 不能删除");
				}
		    },
		    cancelValue: '取消',
		    cancel: function () {}
		});

		return false;//false时不执行onRemove方法
	}
	//删除事件
	function onRemove(e, treeId, treeNode) {
		 alert("删除");
	}



	/**
	*显示按钮【ztree】
	*/
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0){
			return;
		}
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId+ "' title='添加' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){ //添加按钮触发的事件
			var url = "<c:url value='/category/save.html' />";  //章节添加后台URL
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = treeNode.children;
			var newId = uuid();
			addTreeChildrenNode_ht(zTree,url,treeNode,newId);
		});
	};
	/**
	*隐藏按钮 【ztree】
	*/
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
	};


	/**
	*主函数
	*/
	$(function() {
		var nodesJson = toJson('${treeJson}');
		if(!$.isEmptyObject(nodesJson)){
		    //$("#openRootTree").attr("disabled",true);
			$("#openRootTree").hide();
			$.fn.zTree.init($("#treeDemo"), setting, nodesJson);
		}
	});
	/**
	*添加根节点
	**/
	function createTreeRoot(){
		var url = "<c:url value='/category/save.html' />";  //章节添加后台URL
		addTreeRootNode_ht("treeDemo",url);
	}

	var orlNodeList=[],newNodeList=[];
	/**
	*查询节点
	*
	*/
	function searchNode(){
		var nodesName = $("#nodesName").val();
		if(nodesName =="" ||nodesName ==null ){
			return ;
		}

		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		//取消上次查询的样式
		for( var i=0;i<orlNodeList.length;i++) {
			orlNodeList[i].highlight = false;
			treeObj.updateNode(orlNodeList[i]);
		}


		var newNodeList = treeObj.getNodesByParamFuzzy("name",nodesName, null);
		for( var i=0; i<newNodeList.length;i++) {
			newNodeList[i].highlight = true;
			var isParent = newNodeList[i].isParent;
			if(isParent){  //展开节点
				treeObj.expandNode(newNodeList[i], true, true, true);
			}else{//展开父节点
				var parentNode = newNodeList[i].getParentNode();
				treeObj.expandNode(parentNode, true, true, true);
			}
			treeObj.updateNode(newNodeList[i]);
		}
		orlNodeList = newNodeList;
	}
	//获取uuid
	function uuid() {
		var s = [];
		var hexDigits = "0123456789abcdef";
		for (var i = 0; i < 36; i++) {
		s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
		}
		s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
		s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
		s[8] = s[13] = s[18] = s[23] = "";

		var uuid = s.join("");
		return uuid;
	}
	
	function categoryInput(){
		parent.openIframePage("导入知识点","<c:url value='/category/categoryinput.html' />");
	}
</script>
<!-- main区域   begin-->
<div class="headbar">
	    <!-- 操作域  -->
		<div class="operating" style="position:relative; overflow:visible ">
		 	<a href="javascript:void(0)" onclick="createTreeRoot();"><button class="operating_btn" type="button" id="openRootTree"><span class="addition">添加根节点</span></button></a>
		 	<div class="search f_l">
		 	 节点名称：
			<input type="text" id="nodesName" class="file" />
			<input type="button" class="submit" value="搜索" onclick="searchNode();"/>
			</div>
			<div class="t_r">
				<a href="javascript:void(0)" onclick="categoryInput();"><button class="operating_btn" type="button"><span class="export">导入</span></button></a>
			</div>
      	</div>
		<div id="treeDemo" class="ztree" style="padding-left: 20px; max-height: 400px;overflow-y:auto;"></div>
</div>
