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
		var url = "<c:url value='/user/list.html' />";
		window.parent.document.getElementById("content_ifr").src = url;
	}

	//更新
	function updateuser(aid){
		var id = aid;
		$.ajax({
			url : "<c:url value='/user/delete.action'/>",
			data : 'id='+id,
			dataType : 'json',
			type : 'POST',
			cache : false,
			success : function(data) {
				if (data.success) {
					location.href="<c:url value='/user/list.html'/>";
				} else {
					var meg = data.message;
					if(typeof(meg) =='undefined'){
						alert("更新用户信息错误，请联系管理员！");
					}else{
						alert(data.message);
					}
					return false;
				}
			}, 
			error : function(data) {
				var meg = data.message;
				if(typeof(meg) =='undefied'){
					alert("更新用户信息错误，请联系管理员！");
				}else{
					alert(data.message);
				}
				return false;
			},
			async:false
		});
	}
	
	//删除
	function deleteuser(id){
			$.ajax({
			url : "<c:url value='/user/delete.action'/>",
			data : 'id='+id,
			dataType : 'json',
			type : 'POST',
			cache : false,
			success : function(data) {
				if (data.success) {
					location.href="<c:url value='/user/list.html'/>";
				} else {
					var meg = data.message;
					if(typeof(meg) =='undefined'){
						alert("删除用户信息错误，请联系管理员！");
					}else{
						alert(data.message);
					}
					return false;
				}
			}, 
			error : function(data) {
				var meg = data.message;
				if(typeof(meg) =='undefied'){
					alert("删除用户信息错误，请联系管理员！");
				}else{
					alert(data.message);
				}
				return false;
			},
			async:false
		});
	}

	//修改
	function updateuser(id){ 
		var url = "<c:url value='/user/handle.action?id="+id+"' />";
		window.parent.document.getElementById("content_ifr").src = url;
	}

	//配置角色
	function allocateRole(userdata){ 
		var id = userdata.split("&")[0];
		var viewText = userdata.split("&")[1];
		if(viewText==null||viewText=="null")
			viewText="";
		var url = "<c:url value='/user/allocate.action?id="+id+"&viewText="+viewText+"' />";
		window.parent.document.getElementById("content_ifr").src = url;
	}
	
	//查询方法
	function query(type){
		//清空列表
		$("tbody").empty();
		var uname = $("#uname").val();
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
		var url = "<c:url value='/user/query.html' />";
		$.ajax({
			url : url ,
			data : $("#dataform").serialize(),
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
					tbodyDiv+="<td><input type='checkbox' name='userBox' value='"+tableJson[i].id+"' /></td>";
					tbodyDiv+="<td>"+tableJson[i].uname+"</td>";
					tbodyDiv+="<td>"+tableJson[i].phone+"</td>";
					tbodyDiv+="<td>"+tableJson[i].regdate+"</td>";
					tbodyDiv+="<td>"+tableJson[i].email+"</td>";
					tbodyDiv+="<td>"+tableJson[i].organization.name+"</td>";
					tbodyDiv+="<td>"+tableJson[i].viewText+"</td>";
					tbodyDiv+="<td>"+tableJson[i].lastLoginTime+"</td>";
					tbodyDiv+="<td>"+
					 			"<img class='betn_edit' title='修改'  onclick='updateuser(\""+tableJson[i].id+"\")'/>"+
					 			"<img class='betn_del' title='删除' onclick='deleteuser(\""+tableJson[i].id+"\")'/>"+
					 			"<img class='betn_detail' title='配置角色' onclick='allocateRole(\""+tableJson[i].id+"&"+tableJson[i].viewText+"\")'/>"+
					 			"</td>";
					tbodyDiv+="</tr>";
					$("tbody").append(tbodyDiv);
				}
			}
		});
	}

	/**进入到用户添加页*/
	function openAddPage(){
		var url = "<c:url value='/user/add.html'/>";
		window.parent.document.getElementById("content_ifr").src = url;
	}
	/**批量删除用户*/
	function multi_deleteUser(){
		 var array= new Array();
		 $("input[name='userBox']").each(function() {
			 if ($(this).attr('checked')) {
				 array.push($(this).val());
			 }		       
		});
		var userIds =  array.join(',');
		if(confirm("是否需要批量删除用户?")){
			deleteuser(userIds);
		}
	}
</script>
<!-- main区域   begin-->
<div class="headbar">
	    <!-- 操作域  -->
		<div class="operating" style="position:relative; overflow:visible ">
  	    	 <a href="javascript:void(0)" onclick="selectAll('userBox');"><button class="operating_btn" type="button"><span class="sel_all">全选</span></button></a>
		 	 <a href="javascript:void(0)" onclick="openAddPage();"><button class="operating_btn" type="button"><span class="addition">添加</span></button></a>
     	     <a href="javascript:void(0)" onclick="multi_deleteUser();"><button class="operating_btn" type="button"><span class="delete">批量删除</span></button></a>
     	     <div class="search f_r">
				<form id="dataform" name="dataform" method="get">
				             用户电话:
	              	<input type="text" name="phone" id="phone"/>
	              	所属部门:
	              	<input type="text" name="organization.name" id="orgname" onclick="loadOrgTree();" readonly="true"/>
	              	<input type="hidden" name="organization.id" id="orgid"/>
	              	 用户名称:
	              	<input type="text" name="uname" id="uname"/>
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
                	 <th><input type='checkbox' name='userBox' value='' onclick="selectAll('userBox');"/ ></th>
					 <th>用户名称</th>
					 <th>电话</th>
					 <th>注册日期</th>
					 <th>email</th>
					 <th>所在部门</th>
					 <th>所属角色</th>
    				 <th>上次登录时间</th>
    				 <th>操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div><%@ include file="/pages/organization/organizationTree.jsp"%>
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
