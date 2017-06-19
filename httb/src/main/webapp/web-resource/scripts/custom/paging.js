// 上一页、下一页按钮 跳转 
function goPagePrefix(num) {
	var currentPage = $("#currentPage").val();
	var page = Number(currentPage) + Number(num);
	$("#currentPage").val(page);
	submitForm();
}
// 确定页 跳转
function goPage(num) {
	var page = Number(num);
	$("#currentPage").val(page);
	submitForm();
}
// 计算页数 总天数，每页多个条
function calculatePage(total, pageSize) {
	var page = total / pageSize;
	if (total % pageSize > 0)
		return Math.ceil(page);
	else
		return page;
}
// 获取分页列表   ********************* 
// 参数:总条数   每页条数   第几页    页码个数
function make_page_list(total, pageSize, currentPage, page_sum) {
	var total_page = calculatePage(total, pageSize);
	var html = '<span>第' + currentPage + '页/共' + total_page+'页</span>  ' ;
	if (total_page > 1) {
		if (currentPage > 1) {
			html += '<a onclick="getPage(1)"  title="首页" class="page-prev">首页</a>';
		}
		if (currentPage > 1) {
			// html += '<a onclick="goPagePrefix(1)"  title="上一页"  class="page-prev">上一页</a>';
		}
		var page = Math.floor(page_sum / 2);//当前页的 前后页码
		var begin = currentPage - page;//开始页
		var end = currentPage + page;//结束页
		begin = begin < 1 ? 1 : begin;
		var temp = end - begin;
		if (temp < (page_sum - 1)) {
			temp = page_sum - temp - 1;
			end = end + temp;
		}
		if (parseInt(end) > parseInt(total_page)) {
			temp = end - total_page;
			begin = begin - temp;
			end = total_page;
			begin = begin < 1 ? 1 : begin;
		}
		for (var c = begin; c <= end; c++) {
			if (c == currentPage) {
				html += '<a class="current">'+c+'</a>';
			} else {
				html += '<a onclick="getPage('+c+')">'+c+'</a>';
			}
		}
		if (currentPage < total_page) {
			// html += '<a onclick="goPagePrefix(-1)" title="下一页" class="page-next">下一页</a>';
		}
		if (currentPage < total_page){
			html += '<a onclick="getPage('+total_page+')" title="末页" class="page-next">末页</a>';
		}
	}
	$("#pagnation").html(html);

}