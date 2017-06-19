<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- dhtmlx.popup -->
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxcommon.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxpopup.js' />"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxpopup_dhx_terrace.css' />" media="all" />

<script type="text/javascript">
/*************************CheckboxPopup选择树  begin************************************************************************/
var treePopup;
/**知识点选择树
 *inp --标签对象
 *checkedIds --已经选中id
 **/
function funPopupCheckboxTree(inp,checkedIds){
	var url_ht = "<c:url value='/category/getCategoryTree.html' />";
	if (!treePopup) {
		$.ajax({
			url : url_ht ,
			data : {"checkedIds":checkedIds},
			dataType : 'json',
			type : 'POST',
			cache : false,
			success : function(response) {
				if (response.success) {
					funCreateTree_ht(true,"treeDemo",response.data,null,onTreeCheckFun,beforeCheck);
				}else{
					$("#treeDemo").html(response.message);
				}
			}, 
			error : function(response) {
				$("#treeDemo").html("服务器错误,请联系管理员");
			}
		});
		treePopup = new dhtmlXPopup({ mode: "right",skin :"dhx_terrace"});
		treePopup.attachObject("popupDiv");
    }
    if (treePopup.isVisible()) {
    	treePopup.hide();
    	
    } else {
    	var x = getAbsoluteLeft(inp);
        var y = getAbsoluteTop(inp);
        var w = inp.offsetWidth;
        var h = inp.offsetHeight;
        treePopup.show(x, y, w, h);
    }
}
//多选前的事件
function beforeCheck(treeId, treeNode) {
	return !treeNode.isParent;
}
//获取checkbox叶子节点选择的
function filter(node) {
    return (!node.isParent && node.checked);
}
/*************************CheckboxPopup选择树  end************************************************************************/

</script>
<!-- 弹出框指定DIV popupDiv -->
<div id="popupDiv" style="display: none;">
<span style="background-color: #3a87ad;color: #FFF7FB">只可以选择叶子节点</span><br />
<div id="treeDemo" class="ztree" style="width:340px; max-height: 200px;overflow: auto;">加载中...</div>
</div>
