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
	});

	//重置
	function resetSearch(){
		$("#name").val();
	}

	//更新
	function updaterole(id){
		var url = "<c:url value='/role/handle?id="+id+"' />";
		window.parent.document.getElementById("content_ifr").src = url;
	}
	
	//删除
	function deleterole(aid){
		var id = aid;
		$.ajax({
			url : "<c:url value='/role/delete.action'/>",
			data : 'id='+id,
			dataType : 'json',
			type : 'POST',
			cache : false,
			success : function(data) {
				if (data.success) {
					location.href="<c:url value='/role/list.html'/>";
				} else {
					var meg = data.message;
					if(typeof(meg) =='undefined'){
						alert("删除角色信息错误，请联系管理员！");
					}else{
						alert(data.message);
					}
					return false;
				}
			}, 
			error : function(data) {
				var meg = data.message;
				if(typeof(meg) =='undefied'){
					alert("删除角色信息错误，请联系管理员！");
				}else{
					alert(data.message);
				}
				return false;
			},
			async:false
		});
	}

	//分配菜单权限
	function allocateMenu(id){ 
		var url = "<c:url value='/menu/authorizeMenu?id="+id+"' />";
		window.parent.document.getElementById("content_ifr").src = url;
	}

	//查询方法
	function query(type){
		//清空列表
		$("tbody").empty();
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
		var name = $("#name").val();
		var url = "<c:url value='/role/query.html' />";
		$.ajax({
			url : url ,
			data : $("#dataform").serialize(),
			dataType : 'json',
			type : 'GET',
			cache : false,
			success : function(pageJson) {
				$("#pageSum").html(pageJson.total);
				$("#pageNum").html($("#pageNumHidden").val());
				var tableJson = pageJson.rows;
				var tbodyDiv ;
				for(var i=0;i<tableJson.length;i++){
					tbodyDiv = ("<tr>");
					tbodyDiv+="<td><input type='checkbox' name='roleBox' value='"+tableJson[i].id+"' /></td>";
					tbodyDiv+="<td style='width: 200px;'>"+tableJson[i].name+"</td>";
					tbodyDiv+="<td>"+tableJson[i].description+"</td>";
					tbodyDiv+="<td>"+
					 			"<img class='betn_edit' title='修改'  onclick='updaterole(\""+tableJson[i].id+"\")'/>"+
					 			"<img class='betn_del' title='删除' onclick='deleterole(\""+tableJson[i].id+"\")'/>"+
					 			"<img class='betn_detail' title='配置菜单' onclick='allocateMenu(\""+tableJson[i].id+"\")'/>"+
								"</td>";
					tbodyDiv+="</tr>";
					$("tbody").append(tbodyDiv);
				}
			}
		});
	}
	
	/**进入到角色添加页*/
	function openAddPage(){
		var url = "<c:url value='/role/add.html'/>";
		window.parent.document.getElementById("content_ifr").src = url;
	}
	
	function multi_deleteRole(){
		 var array= new Array();
		 $("input[name='roleBox']").each(function() {
			 if ($(this).attr('checked')) {
				 array.push($(this).val());
			 }		       
		});
		var roleIds =  array.join(',');
		if(confirm("是否需要批量删除角色?")){
			deleterole(roleIds);
		}
	}
	
</script>
<!-- main区域   begin-->
<div class="headbar">
	    <!-- 操作域  -->
		<div class="operating" style="position:relative; overflow:visible ">
  	    	 <a href="javascript:void(0)" onclick="selectAll('roleBox');"><button class="operating_btn" type="button"><span class="sel_all">全选</span></button></a>
		 	 <a href="javascript:void(0)" onclick="openAddPage();"><button class="operating_btn" type="button"><span class="addition">添加</span></button></a>
     	     <a href="javascript:void(0)" onclick="multi_deleteRole();"><button class="operating_btn" type="button"><span class="delete">批量删除</span></button></a>
     	     <div class="search f_r">
				<form id="dataform" name="dataform" method="get">
	              	 角色名称:
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
                	 <th><input type='checkbox' name='roleBox' value='' onclick="selectAll('roleBox');" /></th>
					 <th style="width: 200px;">角色名称</th>
    				 <th>备注信息</th>
    				 <th>操作</th>
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
