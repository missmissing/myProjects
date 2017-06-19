<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${TITLE }</title>
<%@ include file="/common/common.jsp"%>
<script type="text/javascript">
	$(function() {
		queryList(); //初始化列表
	});
//批量删
function multi_delete(){
	var  checkArr = $("tbody :checkbox[checked]");
    if(checkArr.length<1){
    	alert("请选择试题");
    }else{
    	var url = "<c:url value='/userfavor/deleteBatch.html' />";
    	funDeleteRowBatch(url,checkArr);
    }
    
}

//查询按钮
function funQuery(){
	queryList();
}
//重置按钮
function funClear(){
	$('#serachUserFavor')[0].reset();
}
//删除试题
function deleteQustion(id,inp){
	var url = "<c:url value='/userfavor/delete.html' />";
	funDeleteRow(url,id,inp);
}

/************ 异步加载分页 begin****************/
function queryList(){
	$("tbody").remove();
	var url = "<c:url value='/userfavor/query.html' />";
	$.ajax({
		url : url ,
		data : $("#serachUserFavor").serialize(),
		dataType : 'json',
		type : 'POST',
		cache : false,
		success : function(pageJson) {
			var tableJson = pageJson.rows;
			var tbodyDiv="<tbody>";
			for(var i=0;i<tableJson.length;i++){
				tbodyDiv+="<tr>";
				tbodyDiv+="<td><input type='checkbox' name='id[]' value='"+tableJson[i].qid+"' /></td>";
				var qcontent = tableJson[i].qcontent == null ? "" : tableJson[i].qcontent;
				tbodyDiv+="<td>"+qcontent+"</td>";
				tbodyDiv+="<td>"+tableJson[i].qyear+"</td>";
				var type = typeof(quetypesJsonMap[tableJson[i].qtype]) == "undefined"?"":quetypesJsonMap[tableJson[i].qtype];
				tbodyDiv+="<td>"+type+"</td>";
				var attribute = typeof(attributeJsonMap[tableJson[i].qattribute]) == "undefined"?"":attributeJsonMap[tableJson[i].qattribute];
				tbodyDiv+="<td>"+attribute+"</td>";
				var status = typeof(attributeJsonMap[tableJson[i].qstatus]) == "undefined"?"":attributeJsonMap[tableJson[i].qstatus];
				tbodyDiv+="<td>"+status+"</td>";
				
				tbodyDiv+="<td>"+funDateFormat(tableJson[i].createtime)+"</td>";
				tbodyDiv+="<td>"+
							"<p class='betn_check' title='预览' onclick='viewQuestion(\""+tableJson[i].qid+"\")'/>"+
					 		"<p class='betn_edit' title='修改' onclick='editQuestion(\""+tableJson[i].qid+"\")' />"+
							"<p class='betn_del' title='删除' onclick='deleteQustion(\""+tableJson[i].qid+"\",this)'/>"+
					        "</td>";
				tbodyDiv+="</tr>";
			}
			tbodyDiv+="</tbody>";
			$("#list_table").append(tbodyDiv);
			lineColour_ht();//隔行变色
		}
	});
}
/************ 异步加载分页 end****************/
</script>
<!-- main区域   begin-->
<div class="headbar">
	    <!-- 操作域  -->
		<div class="operating" style="position:relative; overflow:visible ">
  	    	 <a href="javascript:void(0)" onclick="selectAll('id[]');"><button class="operating_btn" type="button"><span class="sel_all">全选</span></button></a>
     	     <a href="javascript:void(0)" onclick="multi_delete();"><button class="operating_btn" type="button"><span class="delete">批量删除</span></button></a>
     	     <div class="search f_r">
				<form id="serachUserFavor" method="post">
					用户名:
					<input id="knowledgename" type="text" class="file"/>
	              	<input type="button" class="submit" onclick="funQuery();" value="搜索"/>
	              	<input type="button" class="btn" onclick="funClear();" value="重置" />
				</form>
			</div>
      	</div>
      	<div class="field">
		<table class="list_table">
			<col width="40px" /><col/>
			<thead>
				<tr>
                	 <th></th>
                	 <th>用户名</th>
					 <th>错题集</th>
    				 <th>操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
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