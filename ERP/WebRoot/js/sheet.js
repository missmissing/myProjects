$(function(){
	$('#grid').datagrid({
		url:'sheet_getOrderSheet.action',
		singleSelect:true,
		columns:[[
		           {field:'name',title:'商品分类',width:200},
		           {field:'money',title:'销售金额',width:200}
		         ]]
	});
});

/**
 * 得到销售报表和统计图
 */
function searchSheet(){
	//获得表单提交的查询数据
	var formdata = getFormData('searchForm');
	$('#grid').datagrid('load',formdata);
	//得到统计图表
	$('#chart').attr('src','sheet_getOrderChart.action?date1='+ formdata['date1']+'&date2='+formdata['date2']);
}

/**
 * 导出Excel表格
 */
function exportExcel(){
	//得到表单数据
	var formdata = getFormData('searchForm');
	window.open('sheet_exportExcel.action?date1='+formdata['date1']+'&date2='+formdata['date2']);
}
