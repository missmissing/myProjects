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
		//初始化 异步加载分页
		query();
		//make_page_list(200,10,1,5);
	});

	//重置
	function resetSearch(){
		$("#name").val();
	}

	//删除
	function deleteorg(aid){
		var id = aid;
		$.ajax({
			url : "<c:url value='/organization/delete.action'/>",
			data : 'id='+id,
			dataType : 'json',
			type : 'POST',
			cache : false,
			success : function(data) {
				if (data.success) {
					location.href="<c:url value='/organization/list.html'/>";
				} else {
					var meg = data.message;
					if(typeof(meg) =='undefined'){
						alert("删除组织机构信息错误，请联系管理员！");
					}else{
						alert(data.message);
					}
					return false;
				}
			}, 
			error : function(data) {
				var meg = data.message;
				if(typeof(meg) =='undefied'){
					alert("删除组织机构信息错误，请联系管理员！");
				}else{
					alert(data.message);
				}
				return false;
			},
			async:false
		});
	}

	//修改
	function updateorg(id){
		var url = "<c:url value='/organization/handle?id="+id+"' />";
		window.parent.document.getElementById("content_ifr").src = url;
	}

	//查询方法
	function query(type){
		//清空列表
		$("tbody").empty();
		var name = $("#name").val();
		if(type == '-1'){
			if($("#pageNum").html()-1 == 0)
				$("#pageNumHidden").val(1) ;
			else
			    $("#pageNumHidden").val($("#pageNum").html()-1) ;			
		}else if(type == '+1'){
			if(Number($("#pageNum").html())+1 > Number($("#pageSum").html()) )
				$("#pageNumHidden").val($("#pageSum").html()) ;
			else
				$("#pageNumHidden").val(Number($("#pageNum").html())+1) ;	
		}else if(type == 'first'){
			$("#pageNumHidden").val(1) ;
		}else if(type == 'last'){
			$("#pageNumHidden").val($("#pageSum").html()) ;
		}else if(type == 'gotoPage'){
			if($("#gotoPage").val() > $("#pageSum").html())
				$("#pageNumHidden").val($("#pageSum").html()) ;
			else
			    $("#pageNumHidden").val($("#gotoPage").val()) ;
		}
		var url = "<c:url value='/organization/query' />";
		$.ajax({
			url : url ,
			data : $("#orgaForm").serialize(),
			dataType : 'json',
			type : 'POST',
			cache : false,
			success : function(pageJson) {
				$("#pageSum").html(pageJson.total);
				$("#pageNum").html($("#pageNumHidden").val());
				var tableJson = pageJson.rows;
				var tbodyDiv ;
				for(var i=0;i<tableJson.length;i++){
					tbodyDiv = ("<tr>");
					tbodyDiv+="<tr>";
					tbodyDiv+="<td><input type='checkbox' name='orgBox' value='"+tableJson[i].id+"' /></td>";
					tbodyDiv+="<td style='width: 200px;'>"+tableJson[i].name+"</td>";
					tbodyDiv+="<td style='width: 200px;'>"+tableJson[i].description+"</td>";
					tbodyDiv+="<td>"+tableJson[i].organizationNo+"</td>";
					tbodyDiv+="<td>"+
					 		"<img class='betn_edit' title='修改' onclick='updateorg(\""+tableJson[i].id+"\")' />"+
							"<img class='betn_del' title='删除' onclick='deleteorg(\""+tableJson[i].id+"\")'/>"+
					        "</td>";
					tbodyDiv+="</tr>";
					$("tbody").append(tbodyDiv);
				}
			}
		});
	}

	/**进入到试题添加页*/
	function openAddPage(){
		var url = "<c:url value='/organization/add.html'/>";
		window.parent.document.getElementById("content_ifr").src = url;
	}
	
	function multi_deleteOrg(){
		 var array= new Array();
		 $("input[name='orgBox']").each(function() {
			 if ($(this).attr('checked')) {
				 array.push($(this).val());
			 }		       
		});
		var userIds =  array.join(',');
		if(confirm("是否需要批量删除机构?")){
			deleteorg(userIds);
		}
	}

</script>
<!-- main区域   begin-->
<div class="headbar">
	    <!-- 操作域  -->
		<div class="operating" style="position:relative; overflow:visible ">
  	    	 <a href="javascript:void(0)" onclick="selectAll('orgBox');"><button class="operating_btn" type="button"><span class="sel_all">全选</span></button></a>
		 	 <a href="javascript:void(0)" onclick="openAddPage();"><button class="operating_btn" type="button"><span class="addition">添加</span></button></a>
     	     <a href="javascript:void(0)" onclick="multi_deleteOrg();"><button class="operating_btn" type="button"><span class="delete">批量删除</span></button></a>
     	     <div class="search f_r">
				<form id="orgaForm" name="orgaForm" method="get">
	              	 机构名称:
	              	<input type="text" name="name" id="name"/>
	              	<input type="hidden" id="pageNumHidden" name="pageNum" value="1"/>
	              	<input type="button" class="btn" onclick="query();" value="查询"/>
	              	<input type="reset" class="btn" onclick="resetSearch();" value="重置"/>
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
                	 <th><input type='checkbox' name='orgBox' value=''  onclick="selectAll('orgBox');"/></th>
					 <th style="width: 25%;">组织机构名称</th>
    				 <th style="width: 25%;">组织机构名说明</th>
    				 <th style="width: 25%;">组织机构编号</th>
    				 <th style="width: 25%;">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<!-- 列表内容域 -->
<div class="content" style="height: 265px;">
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
<div class="field">    	
					<a href="javascript:void(0)" onclick="query('first')">首页</a>&nbsp;&nbsp;
    				<a href="javascript:void(0)" onclick="query('-1')">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    				<span id="pageNum">1</span><span>/</span><span id="pageSum"> </span>
    				<a href="javascript:void(0)" onclick="query('+1')">下一页</a>&nbsp;&nbsp;
    				<a href="javascript:void(0)" onclick="query('last')">末页</a>&nbsp;&nbsp;
    				<a href="javascript:void(0)" onclick="query('gotoPage')">跳转至</a><input type="text" id="gotoPage" style="width: 20px"/>
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
