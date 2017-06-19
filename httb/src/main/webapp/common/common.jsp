<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=100" />

<!-- jquery -->
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery/jquery-1.7.2.min.js' />"></script>

<!-- ckeditor -->
<script type="text/javascript" src="<c:url value='/web-resource/scripts/ckeditor/ckeditor.js' />"></script>

<!-- artDialog -->
<script type="text/javascript" src="<c:url value='/web-resource/scripts/artDialog/jquery.artDialog.js?skin=twitter' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/artDialog/iframeTools.js' />"></script>
<!-- custom -->
<script type="text/javascript" src="<c:url value='/web-resource/scripts/custom/tree.js' />"></script>
<%-- <script type="text/javascript" src="<c:url value='/web-resource/scripts/custom/colorpicker.js' />"></script> --%>
<%-- <script type="text/javascript" src="<c:url value='/web-resource/scripts/custom/content_form.js' />"></script> --%>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/custom/menu.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/custom/paging.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/custom/editor.js' />"></script>
<!-- jquery-form -->
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery.form/jquery.form.js' />"></script>

<!-- jquery.validate -->
<link rel="stylesheet" type="text/css" href="<c:url value='/web-resource/scripts/jquery.validate/validationEngine.jquery.css' />" media="all" />
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery.validate/jquery.validationEngine-zh_CN.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery.validate/jquery.validationEngine.js' />"></script>

<!-- jquery.zTree -->
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/web-resource/scripts/ztree/zTreeStyle.css' />" media="all" />
<script type="text/javascript" src="<c:url value='/web-resource/scripts/ztree/jquery.ztree.all-3.5.min.js' />"></script>

<!-- Date -->
<link rel="stylesheet" type="text/css" href="<c:url value='/web-resource/scripts/My97DatePicker/skin/WdatePicker.css' />" media="all" />
<script type="text/javascript" src="<c:url value='/web-resource/scripts/My97DatePicker/WdatePicker.js' />"></script>

<!-- jquery.PrintArea -->
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery.PrintArea/jquery.PrintArea.js' />"></script>

<!-- Self common style-->
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/web-resource/styles/theme/content.css' />" media="all" />

<!-- Self page -->
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/web-resource/styles/page/content.css' />" media="all" />
<script type="text/javascript">

/**1 -json转换 */
var toJson = function(str){ 
	var json = eval('(' + str + ')'); 
	return json; 
};
	
/**2 -重写alert*/
window.alert = function(myinfo){
	art.dialog.alert(myinfo+"");
}

/**3 -隔行换色*/
 var lineColour_ht = function(){
	 $(".list_table tr::nth-child(even)").addClass('even');
		$(".list_table tr").hover(
			function () {
				$(this).addClass("sel");
			},
			function () {
				$(this).removeClass("sel");
			}
		);
 }
/**
 * 4 生成年份列表
 *selectDiv --select标签ID
 */
var funYearList_ht = function(selectDiv){
	var date = new Date();
 	var year = 2017;  //date.getFullYear()+2;  //--临时写死
	var optionDiv ="<option value='' >-请选择-</option>";
	for(var i=0; i<=17; i++){
		var num = year-i;
		optionDiv +="<option value='"+num+"'>"+num+"</option>";
	}
	$("#"+selectDiv).html(optionDiv);
}
/**
 *5  表单提交校验
 */
var validationEngine =function(formId) {
	$("#" + formId).validationEngine({
		showOneMessage:true,
		autoPositionUpdate:true, //自动改变位置
		addPromptClass:'formError-text' ,//white small text
		autoHidePrompt:	false,  //提示自动隐藏
		autoHideDelay:5000,//隐藏时间
		promptPosition:"inline",      
		scroll:false 
	});
}
//6 全选全不选
function selectAll(nameVal)
{
	//获取复选框的form对象
	var formObj = $("form:has(:checkbox[name='"+nameVal+"'])");
	//根据form缓存数据判断批量全选方式
	if(formObj.data('selectType')=='none' || formObj.data('selectType')==undefined)
	{
		$(":checkbox[name='"+nameVal+"']:not(:checked)").attr('checked','checked');
		formObj.data('selectType','all');
	}
	else
	{
		$(":checkbox[name='"+nameVal+"']").removeAttr("checked");;
		formObj.data('selectType','none');
	}
}
/** 7 批量删除 ---【仅用于table删除行】
 *  url ---删除地址
 *  checkArr -- 删除的选项域集合
 */
var funDeleteRowBatch = function(url,checkArr){
	var valArr = ""; //选择值
	$(checkArr).each(function(i){
        valArr[i] = $(this).val();
        if(i == checkArr.length-1){
        	valArr+=$(this).val();
        }
        else{
        	valArr+=$(this).val()+",";
        }
    });
	$.post(url,{"ids":valArr}, function(response) {
		if (response.success) {
			art.dialog.tips(response.message+" 可重新搜索刷新列表");
			for(var m=0;m<checkArr.length;m++){
				$(checkArr[m]).parents("tr").remove();
			}
		}else{
			alert(response.message);
		}

	}, "json");
}
/** 8 删除行--【仅用于table删除行】
 *  url ---删除url
 *  id -- 删除的主键ID
 *  inp -- 按钮对象
 */
var funDeleteRow = function(url,id,inp){
	$.post(url,{"id":id}, function(response) {
		if (response.success) {
			art.dialog.tips(response.message+" 可重新搜索刷新列表");
			$(inp).parents("tr").remove();
		}else{
			alert(response.message);
		}

	}, "json");
}
/** 8 时间格式化
 *  time -- 时间值（如 毫秒 ,Thu Nov 9 20:30:37 UTC+0800 2008）
 */
var funDateFormat = function(time){
    var datetime = new Date(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
}
/** iframe自适应高度 */
var minHight = 200; // 最小高度
function iframeHeightSet(obj, height) {
	try {
		var _iframe = null;
		_iframe = obj;
		if (height) {
			_iframe.height = height;
		}
		
		var ih = 0;
		if (_iframe && !window.opera) {
			_iframe.style.display = "block";
			if (_iframe.contentDocument && _iframe.contentDocument.body.offsetHeight) {
				// ns6 syntax
				ih = _iframe.contentDocument.body.offsetHeight + 20;
			} else if (_iframe.Document && _iframe.Document.body.scrollHeight) {
				// ie5+ syntax
				ih = _iframe.Document.body.scrollHeight;
			}
		}
		
		ih = Math.max(minHight, ih);
		_iframe.height = ih*1.25;
	} catch(e) {
		_iframe.height = minHight;
	}
}
</script>
