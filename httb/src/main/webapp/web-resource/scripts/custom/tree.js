/**基于ztree v3.5.17 包含弹出框插件artDialog v4.17*/

/**
 * 添加子节点 注：目前章节模块使用 仅 
 * treeObj tree总对象
 * url_ht -后台添加URl地址
 * fatherNode 父节点对象
 * newId 新建子节点ID
 */
var addTreeChildrenNode_ht = function (treeObj,url_ht,fatherNode,newId){
	art.dialog({
	    content: '<p>请输入子节点名称</p><input id="newnode_ht" style="width:15em; padding:4px" />',
	    fixed: true,
	    shake:true,
	    top: '12%',
	    id: 'newnode_ht',
	    icon: 'question',
	    title:'添加子节点',
	    ok: function () {
	    	var input = document.getElementById("newnode_ht");
	    	if (input.value !== '' && input.value.trim() !==''){
	    		$.ajax({
	    			url : url_ht ,
	    			data : {'category.cid': newId,'category.cname':input.value,"category.cpid":fatherNode.id},
	    			dataType : 'json',
	    			type : 'POST',
	    			cache : false,
	    			success : function(response) {
	    				if (response.success) {
	    					art.dialog.tips(response.message);
	    					treeObj.addNodes(fatherNode, {id:newId, pId:fatherNode.id, name:input.value});
	    				}else{
	    					alert(response.message);
	    				}
	    			}, 
	    			error : function(response) {
	    				alert("服务器错误,请联系管理员");
	    			}
	    		});
	    		
	        } else {
	            this.shake && this.shake();// 调用抖动接口
	            input.select();
	            input.focus();
	            return false;
	        };
	    },
	    cancel: true
	});
};
/**
 * 添加根节点 注：目前章节模块使用
 * treeObj tree总对象
 * url_ht -后台添加URl地址
 * fatherNode 父节点对象
 * newId 新建子节点ID
 */
var addTreeRootNode_ht = function (treeDiv,url_ht){
	art.dialog({
	    content: '<p>请输入根节点名称</p><input id="newnode_ht" style="width:15em; padding:4px" />',
	    fixed: true,
	    shake:true,
	    top: '12%',
	    id: 'newnode_ht',
	    icon: 'question',
	    title:'添加根节点',
	    ok: function () {
	    	var input = document.getElementById("newnode_ht");
	    	if (input.value !== '' && input.value.trim() !==''){
	    		$.ajax({
	    			url : url_ht ,
	    			data : {'category.cid': '001','category.cname':input.value},
	    			dataType : 'json',
	    			type : 'POST',
	    			cache : false,
	    			success : function(response) {
	    				if (response.success) {
	    					art.dialog.tips(response.message);
	    					var zNodes =[{ "id":"001", "pId":"0", "name":input.value}];
	    					$.fn.zTree.init($("#"+treeDiv), setting, zNodes);
	    					$("#openRootTree").hide(); 
	    				}else{
	    					alert(response.message);
	    				}
	    			}, 
	    			error : function(response) {
	    				alert("服务器错误,请联系管理员");
	    			}
	    		});
	    		
	        } else {
	            this.shake && this.shake();// 调用抖动接口
	            input.select();
	            input.focus();
	            return false;
	        };
	    },
	    cancel: true
	});
};
/**
 * 删除节点
 * url_ht -  删除的URL地址
 * treeDiv - 树的显示域 即DIV
 * treeNode - 节点对象
 */
var deleteTreeNode_ht = function(url_ht,treeDiv,treeNode){
	var level = treeNode.level;  //层级
	$.ajax({
		url : url_ht ,
		data : {'category.cid': treeNode.id},
		dataType : 'json',
		type : 'POST',
		cache : false,
		success : function(response) {
			if (response.success) {
				art.dialog.tips(response.message);
				//页面中删除
				var zTree = $.fn.zTree.getZTreeObj(treeDiv);
		    	zTree.removeNode(treeNode);
		    	if(level == 0){
		    		$("#openRootTree").show();
		    	}
			}else{
				alert(response.message);
			}
		}, 
		error : function(response) {
			alert("服务器错误,请联系管理员");
		}
	});
}
/**
 * 修改节点
 * url_ht   - 修改的URL地址
 * treeDiv  - 树的显示域 即DIV
 * treeNode - 节点对象
 */
var updateTreeNode_ht = function(url_ht,treeDiv,treeNode){
	art.dialog({
	    content: '<p>节点【'+treeNode.name+'】修改为：</p><input id="updatenode_ht" style="width:15em; padding:4px" />',
	    fixed: true,
	    shake:true,
	    title:'修改节点',
	    top: '12%',
	    id: 'updatenode_ht',
	    icon: 'question',
	    ok: function () {
	    	var input = document.getElementById("updatenode_ht");
	    	if (input.value !== '' && input.value.trim() !==''){
	    		$.ajax({
	    			url : url_ht ,
	    			data : {'category.cid':treeNode.id,'category.cname':input.value},
	    			dataType : 'json',
	    			type : 'POST',
	    			cache : false,
	    			success : function(response) {
	    				if (response.success) {
	    					art.dialog.tips(response.message);
	    					treeNode.name = input.value;//修改名称
	    					var treeObj = $.fn.zTree.getZTreeObj(treeDiv);
	    					treeObj.updateNode(treeNode);
	    				}else{
	    					alert(response.message);
	    				}
	    			}, 
	    			error : function(response) {
	    				alert("服务器错误,请联系管理员");
	    			}
	    		});
	    		
	        } else {
	            this.shake && this.shake();// 调用抖动接口
	            input.select();
	            input.focus();
	            return false;
	        };
	    },
	    cancel: true
	});
}

/**
*生成树结构
* isChecked - 是否显示多选框
* viewDiv - 指定显示的DIV
* treeJson　－　Tree json数据
* clickFunName -单击事件名称
* checkFunName - 复选框事件名称
* beforeCheck --radio，checkbox 出发前事件
*/
var funCreateTree_ht=function(isChecked,viewDiv,treeJson,clickFunName,checkFunName,beforeCheck){
	//参数
	var setting = {
			check: {
				enable: isChecked
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeCheck: beforeCheck,//check模式下 点击前时间
				onClick: clickFunName,   //单击事件   非check模式下
				onCheck: checkFunName    //check模式下的多选设置
			}
	};
	//设置样式
	$("#"+viewDiv).addClass("ztree");
	//生成树
    $.fn.zTree.init($("#"+viewDiv), setting, treeJson);
};