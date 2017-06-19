<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${TITLE }</title>
<%@ include file="/common/common.jsp"%>
<script type="text/javascript">
	$(function() {
		
		funYearList_ht("pyear");//初始化年份列表
		queryList(); //初始化列表
	});
	/**进入到试卷添加页*/
	function openAddPage(){
		parent.openIframePage("添加试卷","<c:url value='/paper/add.html' />");
	}
	//查询按钮
	function funQuery(){
		queryList();
	}
	//重置按钮
	function funClear(){
		$('#serachPaper')[0].reset();  
	}
	//修改试卷
	function editPaper(pid){
		var url = "<c:url value='/paper/edit.html?pid="+pid+"' />";
		parent.openIframePage("修改试卷",url);
	}
	//试卷选题
	function seleteQues(paperID){
		var url = "<c:url value='/paper/examineSelect.html?pid=' />"+paperID;
		parent.openIframePage("选题",url);
	}
	//删除试卷
	function deletePaper(id,inp){
		var url = "<c:url value='/paper/delete.html' />";
		funDeleteRow(url,id,inp);
	}
	//批量删
	function multi_delete(){
		var  checkArr = $("tbody :checkbox[checked]");
	    if(checkArr.length<1){
	    	alert("请选择试题");
	    }else{
	    	var url = "<c:url value='/paper/deleteBatch.html' />";
	    	funDeleteRowBatch(url,checkArr);
	    }
	    
	}
	
	/************ 异步加载分页 begin****************/
	function queryList(){
		$("tbody").remove();
		var url = "<c:url value='/paper/query4examine.html' />";
		$.ajax({
			url : url ,
			data : $("#serachPaper").serialize(),
			dataType : 'json',
			type : 'POST',
			cache : false,
			success : function(pageJson) {
				var tableJson = pageJson.rows;
				var tbodyDiv="";
				for(var i=0;i<tableJson.length;i++){
					tbodyDiv+="<tr>";
					var status = statusJsonMap[tableJson[i].pstatus];
					if(status != "发布"){			  
						tbodyDiv+="<td><input type='checkbox' name='id[]' value='"+tableJson[i].pid+"' /></td>";

					}else{
						tbodyDiv+="<td></td>";
					}
					tbodyDiv+="<td style='width: 200px;'>"+tableJson[i].ptitle+"</td>";
					if(tableJson[i].pyear == null){
						tbodyDiv+="<td>"+"无"+"</td>";
					}else{
						tbodyDiv+="<td>"+tableJson[i].pyear+"</td>";
					}
					tbodyDiv+="<td>";
					for(var j=0;j<tableJson[i].pareas.length;j++){
						var area = areaJsonMap[tableJson[i].pareas[j]];
						tbodyDiv+=area+",";
					}
					if(tableJson[i].pareas.length == 0){
						tbodyDiv+="无,";
					}
					tbodyDiv = tbodyDiv.substring(0,tbodyDiv.length-1);
					tbodyDiv+="</td>";
					var attribute = attributeJsonMap[tableJson[i].pattribute];
					tbodyDiv+="<td>"+attribute+"</td>";
					
					tbodyDiv+="<td>"+status+"</td>";
					tbodyDiv+="<td>"+tableJson[i].createtime+"</td>";  		
					tbodyDiv+="<td>";
			        if(status != "发布"){			        	
			        	tbodyDiv+="<p class='betn_check' title='审核' onclick='seleteQues(\""+tableJson[i].pid+"\",this)'/>";
			        	tbodyDiv+="<p class='betn_edit' title='修改' onclick='editPaper(\""+tableJson[i].pid+"\")' />";
			        	tbodyDiv+="<p class='betn_del' title='删除' onclick='deletePaper(\""+tableJson[i].pid+"\",this)'/>";			        	
			        }
			        else{
						tbodyDiv+="<p class='betn_down' title='下线' onclick='downPaper(\""+tableJson[i].pid+"\",\""+tableJson[i].pattribute+"\")' />";
			        	tbodyDiv+="<p class='betn_edit' title='修改' onclick='editPaper(\""+tableJson[i].pid+"\")' />";

			        }
			        tbodyDiv+="</td>";   
					tbodyDiv+="</tr>";
				}
				$("#list_table").append(tbodyDiv);
				lineColour_ht();//隔行变色
			}
		});
	}
	/************ 异步加载分页 end****************/
	
	
	//下线
function downPaper(id,pattribute) {
		$.post("<c:url value='/paper/downPaper.html' />",{"id":id,"pattribute":pattribute}, function(response) {
			if (response.success) {
				art.dialog.tips("下线成功");
				funQuery();//重新查询
			}else{
				art.dialog.tips("服务器错误，下线失败");
			}

		}, "json");
	/* 	
		var url = "<c:url value='/question/delete.html' />";
		funDeleteRow(url,id,inp); */
	
}
	
</script>
<!-- main区域   begin-->
<div class="headbar">
	    <!-- 操作域  -->
		<div class="operating" style="position:relative; overflow:visible ">
  	    	 <a style="display:none;" href="javascript:void(0)" onclick="selectAll('id[]');"><button class="operating_btn" type="button"><span class="sel_all">全选</span></button></a>
     	     <a style="display:none;" href="javascript:void(0)" onclick="multi_delete();"><button class="operating_btn" type="button"><span class="delete">批量删除</span></button></a>
		 	 <a style="display:none;" href="javascript:void(0)" onclick="openAddPage();"><button class="operating_btn" type="button"><span class="addition">添加</span></button></a>
<!-- 		 	 <a href="javascript:void(0)" onclick="seleteQues();"><button class="operating_btn" type="button"><span class="filter">选题</span></button></a> -->
     	     <div class="search f_r">
				<form id="serachPaper" method="get">
				       试卷分类:
					<select class="normal" style="width: 80px;"  name="paper.pcategorys" id="pcategorys">
						<option value="" >-请选择-</option>
				        <c:forEach items="${category_Y_Enum}" var="type" varStatus="status">
							<option value="${type.key}">${type.value}</option>
					    </c:forEach>
	              	</select>
					标题:
					<input class="middle" id="psubtitle" type="text" name="paper.psubtitle" />
					地区:
					<select class="normal" style="width: 80px;"  id="parea" name="paper.parea">
						<option value="" >-请选择-</option>
				        <c:forEach items="${areaMap}" var="type" varStatus="status">
							<option value="${type.key}">${type.value}</option>
					    </c:forEach>
	              	</select>
					年份:
					<select class="normal" style="width: 80px;"  id="pyear" name="paper.pyear">
	              	</select>
	              	属性:
					<select class="normal" style="width: 80px;"  id="pattribute" name="paper.pattribute">
				        <option value="" >-请选择-</option>
				        <c:forEach items="${attributeMap}" var="type" varStatus="status">
							<option value="${type.key}">${type.value}</option>
					    </c:forEach>
	              	</select>
	              	 状态:
	              	<select class="normal"  style="width: 80px;" id="pstatus" name="paper.pstatus"  >
	                   	<option value="" >-请选择-</option>
				        <c:forEach items="${statusMap}" var="type" varStatus="status">
							<option value="${type.key}">${type.value}</option>
					    </c:forEach>
				    </select>
	              	<input type="button" class="submit" onclick="funQuery();" value="搜索"/>
	              	<input type="button" class="btn" onclick="funClear();" value="重置" />
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
                	 <th></th>
                	 <th style="width: 200px;">标题</th>
					 <th>年份</th>
    				 <th>地区</th>
    				 <th>属性</th>
    				 <th>状态</th>
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
<!-- main区域   end-->
<script type="text/javascript">
var attributeJsonMap = toJson('${attributeJsonMap}');//属性
var statusJsonMap= toJson('${statusJsonMap}');//状态
var areaJsonMap= toJson('${areaJsonMap}');//地区
</script>
		