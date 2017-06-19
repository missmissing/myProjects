<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${TITLE }</title>
<%@ include file="/common/common.jsp"%>
<!-- dhtmlx.popup -->
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxcommon.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxpopup.js' />"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxpopup_dhx_terrace.css' />" media="all" />

<script type="text/javascript">
	$(function() {
		funYearList_ht("fromyear");
		//初始化 异步加载分页
		query();

		//make_page_list(200,10,1,5);
	});

	//重置
	function resetSearch(){
		$("#imgname").val();
	}

	//删除图片
	function deleteImage(aid){
		$.post("delete.action",{imgid:aid},function(response){
			if (response.success) {
				//重新加载图片列表
				query();
			}else{
				alert(response.message);
			}
		}, "json");

	}

	//修改图片
	function updateImage(aid){
		var url = "<c:url value='/imagemanage/handle.action?imgid="+aid+"' />";
		//window.parent.document.getElementById("content_ifr").src = url;
		parent.openIframePage("修改图片",url);
	}

	//查看图片信息
	function detailImage(aid){
		//重新加载图片列表
		var url = "<c:url value='/imagemanage/detail.action?imgid="+aid+"' />";
		//window.parent.document.getElementById("content_ifr").src = url;
		parent.openIframePage("查看图片信息",url);
	}

	//查询方法
	function query(){
		//清空列表
		$("tbody").empty();
		var fromyear = $("#fromyear").val();
		var imgname = $("#imgname").val();
		var url = "<c:url value='/imagemanage/query.html' />";
		$.ajax({
			url : url ,
			data : 'image.fromyear='+fromyear+'&image.imgname='+imgname,
			dataType : 'json',
			type : 'POST',
			cache : false,
			success : function(pageJson) {
				var tableJson = pageJson.rows;
				var tbodyDiv ;
				for(var i=0;i<tableJson.length;i++){
					tbodyDiv = ("<tr>");
					tbodyDiv+="<tr>";
					tbodyDiv+="<td><input type='checkbox' name='id[]' value='' /></td>";
					tbodyDiv+="<td style='width: 200px;'>"+tableJson[i].imgname+"</td>";
					tbodyDiv+="<td>"+tableJson[i].newimgname+"</td>";
					tbodyDiv+="<td>"+tableJson[i].fromyear+"</td>";
					tbodyDiv+="<td>"+tableJson[i].createtime+"</td>";
					tbodyDiv+="<td>"+
					 		"<img class='betn_detail' title='查看' onclick='detailImage(\""+tableJson[i].aid+"\")' />"+
					 		"<img class='betn_edit' title='修改' onclick='updateImage(\""+tableJson[i].aid+"\")' />"+
							"<img class='betn_del' title='删除' onclick='deleteImage(\""+tableJson[i].aid+"\")'/>"+
					        "</td>";
					tbodyDiv+="</tr>";
					$("tbody").append(tbodyDiv);
				}
			}
		});
	}

	/**进入到试题添加页*/
	function openAddPage(){
		var url = "<c:url value='/imagemanage/handle.html' />";
		//window.parent.document.getElementById("content_ifr").src = url;
		parent.openIframePage("添加图片",url);
	}

</script>
<!-- main区域   begin-->
<div class="headbar">
	    <!-- 操作域  -->
		<div class="operating" style="position:relative; overflow:visible ">
  	    	 <a href="javascript:void(0)" onclick="selectAll('id[]');"><button class="operating_btn" type="button"><span class="sel_all">全选</span></button></a>
		 	 <a href="javascript:void(0)" onclick="openAddPage();"><button class="operating_btn" type="button"><span class="addition">添加</span></button></a>
     	     <div class="search f_r">
				<form action="/imagemanage/query" id="queryimages" name="queryimages" method="post">
					图片年份:
					<select class="normal" name="image.fromyear" id="fromyear">
	              	</select>
	              	 图片名称:
	              	<input type="text" name="image.imgname" id="imgname"/>
	              	<input type="button" class="submit" onclick="query();" value="搜索"/>
	              	<input type="button" class="btn" onclick="resetSearch();" value="重置"/>
				</form>
			</div>
      	</div>
      	<!-- 列表域 -->
      	<div class="field">
      		<!-- 列头信息 -->
		<table class="list_table">
			<col width="40px" /><col/>
			<thead>
				<tr>
                	 <th><input type='checkbox' name='id[]' value='' /></th>
					 <th style="width: 200px;">图片名称</th>
    				 <th>存储名称</th>
    				 <th>图片年份</th>
    				 <th>创建时间</th>
    				 <th>操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<!-- 列表内容域 -->
<div class="content" style="height: 350px;">
	<form action="" method="post" accept-charset="utf-8" id="content_list_form">
		<div style="display:none">
			<input type="hidden" name="dilicms_csrf_token" value="" />
		</div>
	    <table id="list_table" class="list_table">
			<col width="40px" /><col />
			<tbody>
	         </tbody>
      </table>
  </form>
</div>
<div id="popupDiv" style="display: none;">
<span style="background-color: #3a87ad;color: #FFF7FB">只可以选择叶子节点</span><br />
<div id="treeDemo" class="ztree" style="max-width:200px; max-height: 300px;overflow-y: auto;">加载中...</div>
</div>
<!-- 分页条 -->
<!-- <div class="pagnation" id="pagnation"></div> -->
<!-- main区域   end-->
<script type='text/javascript'>
	//隔行换色
	$(".list_table tr::nth-child(even)").addClass('even');
	$(".list_table tr").hover(
		function () {
			$(this).addClass("sel");
		},
		function () {
			$(this).removeClass("sel");
		}
	);
</script>
