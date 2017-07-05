var roleId  //定义角色ID

var clickRow=function(rowIndex, rowData){
	
	roleId=rowData.uuid;  //给全局变量赋值
	$('#tree').tree({
		url:'role_getRoleMenu.action?id='+rowData.uuid,
		animate:true,
		checkbox:true
	});	
	
}


function save(){
	//获得数据
	var nodes = $('#tree').tree('getChecked');
	
	var ids="";
	for(var i=0;i<nodes.length;i++){
		if(i!=0){
			ids+=",";
		}
		ids += nodes[i].id;
	}
	
	var data = {'id':roleId,menuIds:ids};
	$.ajax({
		url:'role_updateRoleMenu.action',
		data:data,
		success:function(value){
			if(value=='ok'){
				$.messager.alert('提示信息','保存成功！');
			}else{
				$.messager.alert('提示信息',value);
			}
		}
			
	});
}