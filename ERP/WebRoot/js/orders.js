var mtbl_index;//主表行索引
var ctbl_index;//从表行索引
$(function(){
	$('#grid').datagrid({
		url:url, 
		pagination:true,
		columns:[[  
	          		{field:'uuid',title:'ID',width:40},
	          		{field:'orderNum',title:'订单编号',width:100},
	          		{field:'createTime',title:'生成日期',width:100,formatter:function(value,row,index){
	          			return new Date(value).Format('yyyy年MM月dd日');
	          		}},
	          		{field:'checkTime',title:'检查日期',width:100,formatter:function(value,row,index){
	          			return new Date(value).Format('yyyy年MM月dd日');
	          		}},
	          		{field:'startTime',title:'开始日期',width:100,formatter:function(value,row,index){
	          			return new Date(value).Format('yyyy年MM月dd日');
	          		}},
	          		{field:'endTime',title:'结束日期',width:100,formatter:function(value,row,index){
	          			return new Date(value).Format('yyyy年MM月dd日');
	          		}},
	          		{field:'orderType',title:'订单类型',width:60,formatter:function(value,row,index){
	          			if(value==1){
	          				return "采购订单";
	          			}else{
	          				return "销售订单";
	          			}
	          		}},
	          		{field:'creater',title:'下单员',width:80,formatter:function(value,row,index){
	          			return emp[value];
	          		}},
	          		{field:'checker',title:'审查员',width:80,formatter:function(value,row,index){
	          			return emp[value];
	          		}},
	          		{field:'starter',title:'采购员',width:80,formatter:function(value,row,index){
	          			return emp[value];
	          		}},
	          		{field:'ender',title:'库管员',width:80,formatter:function(value,row,index){
	          			return emp[value];
	          		}},
	          		{field:'supplierUuid',title:'供应商ID',width:120,formatter:function(value,row,index){
	          			return supplier[value];
	          		}},
	          		{field:'totalNum',title:'总数量',width:50},
	          		{field:'totalPrice',title:'总价格',width:50},
	          		{field:'state',title:'订单状态',width:80,formatter:function(value,row,index){
	          			return state[value];
	          		}},
	          		operation
	           ]],
	           singleSelect:true,
	           view:detailview,
	           detailFormatter:function(index,row){
	        	   return "<table id='div_"+index+"'></table>";
	           },
	           onExpandRow:function(index,row){
	        	   $('#div_'+index).datagrid({
	        		  data:row.orderdetails,
	        		  singleSelect:true,
	        		  columns:[[    
        		          		{field:'uuid',title:'ID',width:100},
        		          		{field:'num',title:'数量',width:100},
        		          		{field:'price',title:'价格',width:100},
        		          		{field:'money',title:'金额',width:100},
        		          		{field:'goodsUuid',title:'商品ID',width:100},
        		          		{field:'goodsName',title:'商品名称',width:100},
        		          		{field:'endTime',title:'出入库时间',width:100,formatter:function(value,row,index){
        		          			return new Date(value).Format("yyyy-MM-dd");
        		          		}},
        		          		{field:'ender',title:'库管员',width:100,formatter:function(value,row,index){
        		          			return emp[value];
        		          		}},
        		          		{field:'storeUuid',title:'仓库编号',width:100},
        		          		{field:'state',title:'状态',width:100,formatter:function(value,row,index){
        		          			if(value=='0'){
        		          				return "未入库";
        		          			}else if(value=='1'){
        		          				return "已入库";
        		          			}
        		          		}}
       		         		  ]],
       		         		//onDblClickRow事件在用户双击一行的时候触发，参数包括：
       		         		//rowIndex：点击的行的索引值，该索引值从0开始。
       		         		//rowData：对应于点击行的记录。
       		         	onDblClickRow:function(rowIndex,rowData){
       		         		//给主表行索引赋值
       		         		mtbl_index=index;
       		         		//给从表行索引赋值
       		         		ctbl_index=rowIndex;   //rowIndex代表双击的子表格的当前行索引
       		         		
       		         		//双击打开订单详细信息的窗口
       		         		$('#orderWindow').window('open');
       		         		//为窗口中的字段赋值
       		         		$('#goodsUuid').html(rowData.goodsUuid);
       		         		$('#goodsName').html(rowData.goodsName);
       		         		$('#uuid').html(rowData.uuid);
       		         		$('#num').html(rowData.num);
       		         		$('#uuid').val(rowData.uuid);  //把隐藏文本框中的属性value设置为订单明细的id
		       		        if(Request['type']=='1')
		       		     	{
		       		     		$('#outstore').hide();
		       		     	}
		       		        if(Request['type']=='2')
		       		     	{
		       		     		$('#instore').hide();	
		       		     	}
       		         	}
	        	     });
	        	   $('#grid').datagrid('fixDetailRowHeight',index);
	           }
	});
	 $('#btnSearch').bind('click',function()
 			{
 				//得到表单的封装值
 				var formdata= getFormData('searchForm');
 				//测试
 				//alert( JSON.stringify( formdata));
 				//调用datagrid的加载数据的方法
 				$('#grid').datagrid('load',formdata);
 			});  
})

function doCheck(uuid){
	$.messager.confirm('提示信息','你确定要审核吗？',function(value){
		if(value)
		{
			$.ajax({
				url:'orders_doCheck.action?id='+uuid,
				success:function(value)
				{
					if(value=='ok')
					{
						$('#grid').datagrid('reload');
					}else
					{
						$.messager.alert('提示信息',value);
					}
					
				}
			});
		}
	});
}

function doConfirm(uuid){
	$.messager.confirm('提示信息','你确定要确认吗？',function(value){
		if(value)
		{
			$.ajax({
				url:'orders_doConfirm.action?id='+uuid,
				success:function(value)
				{
					if(value=='ok')
					{
						$('#grid').datagrid('reload');
					}else
					{
						$.messager.alert('提示信息',value);
					}
					
				}
			});
		}
	});
}

//订单入库
function doInStore(){
	//获得弹出窗口中的数据
	var formdata = getFormData('orderForm');
	$.ajax({
		url:'orderdetail_doInStore.action',
		data:formdata,
		type:'post',
		success:function(value){
			if(value=='ok')
			{
				$.messager.alert('提示信息','入库成功');
				//关闭窗口
				$('#orderWindow').window('close');
				//通过主表删除子表格
				$('#div_'+mtbl_index).datagrid('deleteRow',ctbl_index);
				//判断从表是否删除完毕
				if($('#div_'+mtbl_index).datagrid('getRows').length==0)
				{
					//说明子表格已删除完毕，需要删除主表格
					$('#grid').datagrid('deleteRow',mtbl_index);
				}
				
			}else{
				$.messager.alert('提示信息',value);
			}
		}
	});
}

//订单出库
function doOutStore(){
	//获得弹出窗口中的数据
	var formdata = getFormData('orderForm');
	$.ajax({
		url:'orderdetail_doOutStore.action',
		data:formdata,
		type:'post',
		success:function(value){
			if(value=='ok')
			{
				$.messager.alert('提示信息','出库成功');
				//关闭窗口
				$('#orderWindow').window('close');
				//通过主表删除子表格
				$('#div_'+mtbl_index).datagrid('deleteRow',ctbl_index);
				//判断从表是否删除完毕
				if($('#div_'+mtbl_index).datagrid('getRows').length==0)
				{
					//说明子表格已删除完毕，需要删除主表格
					$('#grid').datagrid('deleteRow',mtbl_index);
				}
				
			}else{
				$.messager.alert('提示信息',value);
			}
		}
	});
}