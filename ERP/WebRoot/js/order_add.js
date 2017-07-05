var nowEditRowIndex;   //定义当前编辑的行
$(function(){
	$('#grid').datagrid({
			columns:[[    
		          		{field:'goodsUuid',title:'商品ID',width:100},
		          		{field:'goodsName',title:'商品名称',width:100,editor:{
		          			   type:'combobox',
		          			   options:{
		          				    url:'goods_list.action',
		          				    valueField:'name',
		          				    textField:'name',
		          				    onSelect:function(record){
			          				    //获得价格编辑框对象
			          				  	var priceEdt = $('#grid').datagrid('getEditor',{index:nowEditRowIndex,field:'price'});
			          				  	
			          				  	if(Request['type']==1){
			          				  		//设置编辑框中的进货价格
				          				  	$(priceEdt.target).val(record.inPrice);
			          				  	}else{
			          				  		//设置编辑框中的出货价格
				          				  	$(priceEdt.target).val(record.outPrice);
			          				  	}
			          				  	
			          				  	
			          				  	
			          				  	//得到并显示商品ID
			          				  	$('#grid').datagrid('getRows')[nowEditRowIndex].goodsUuid=record.uuid;
			          				  	
			          				  	//结束编辑后再开始编辑，目的是为了解决当前行处于编辑状态时，商品ID不显示的问题
			          				  	$('#grid').datagrid('endEdit',nowEditRowIndex);
			          				  	$('#grid').datagrid('beginEdit',nowEditRowIndex);
			          				  	
			          					//重新绑定键盘事件
				          			    bindEditorKeyEnvent();
		          				    }
		          			   }
		          		}},
		          		{field:'price',title:'价格',width:100,editor:'numberbox'},
		          		{field:'num',title:'数量',width:100,editor:'numberbox'},
		          		{field:'money',title:'金额',width:100,editor:'numberbox'},
		          		{field:'-',title:'操作',width:100,formatter:function(value,row,index){
		          			return "<a href='#' onclick='deleteRow("+index+")'>删除</a>";
		          		}}
	     		    ]],
	     	toolbar:[{
	     				iconCls:'icon-add',
	     				text:'添加',
	     				handler:function(){
	     					//关闭上一次处于编辑状态的行
	     					$('#grid').datagrid('endEdit',nowEditRowIndex);
	     					//新增行
	     					$('#grid').datagrid('appendRow',{});
	     					//得到新增行的行号索引
	     					nowEditRowIndex = $('#grid').datagrid('getRows').length-1;
	     					//设置最有一行为编辑状态
	     					$('#grid').datagrid('beginEdit',nowEditRowIndex);
	     					//调用为每行的编辑框绑定键盘事件的方法
	     					bindEditorKeyEnvent();
	     				}
	     	        }],
	     	onClickRow:function(rowIndex,rowData){
				     		//关闭上一次处于编辑状态的行
				     		$('#grid').datagrid('endEdit',nowEditRowIndex);
				     		//将当前选中的行号设置为要编辑的当前行
				     		nowEditRowIndex=rowIndex;
				     		//设置当前行为编辑状态
				     		$('#grid').datagrid('beginEdit',nowEditRowIndex);
				     		//调用为每行的编辑框绑定键盘事件的方法
	     					bindEditorKeyEnvent();S
	     	        },
			singleSelect:true
	});
	
	//==============================================
	//供应商下拉列表
	$('#supplier').combogrid({
		url:'supplier_list.action?t1.type='+Request['type'],
		idField:'uuid',
		textField:'name',
		width:300,
		panelWidth:700,
		columns:[[    
	          		{field:'uuid',title:'ID',width:80},
	          		{field:'name',title:'名称',width:150},
	          		{field:'address',title:'地址',width:150},
	          		{field:'contact',title:'联系人',width:100},
	          		{field:'tele',title:'电话',width:100},
	          		{field:'type',title:'类型',width:50,formatter:function(value,row,index){
						if(value=="1")
						{
							return "供应商";
						}else{
							return "客户";
						}
					}}
     		    ]]
	});
	
});

//提供行内数值输入框件计算的自定义方法
function calculate(){
	//获得价格编辑框对象
	var priceEdt = $('#grid').datagrid('getEditor',{index:nowEditRowIndex,field:'price'});
	//获得价格编辑框中的值（即商品价格）
	var price = $(priceEdt.target).val();
	//获得数量编辑框对象
	var numEdt = $('#grid').datagrid('getEditor',{index:nowEditRowIndex,field:'num'});
	//获得数量编辑框中的值（即商品数量）
	var num = $(numEdt.target).val();
	//获得金额编辑框对象
	var moneyEdt = $('#grid').datagrid('getEditor',{index:nowEditRowIndex,field:'money'});
	//设置金额编辑框中的值
	$(moneyEdt.target).val(price*num);
	//给金额money属性赋值，这样，可以做到在更新时直接就向行对象写值
	$('#grid').datagrid('getRows')[nowEditRowIndex].money=price*num;
}

//为每行的编辑框绑定键盘事件的方法
function bindEditorKeyEnvent(){
	//获得价格编辑框对象
	var priceEdt = $('#grid').datagrid('getEditor',{index:nowEditRowIndex,field:'price'});
	//为价格编辑框对象绑定键盘事件
	$(priceEdt.target).bind('keyup',function(){
		//调用数值输入框计算方法，目的是使金额随着商品价格的变动而自动调整
		calculate();
		sum();  //自动计算总金额
	});
	//获得数量编辑框对象
	var numEdt = $('#grid').datagrid('getEditor',{index:nowEditRowIndex,field:'num'});
	//为数量编辑框对象绑定键盘事件
	$(numEdt.target).bind('keyup',function(){
		//调用数值输入框计算方法，目的是使金额随着商品数量的变动而自动调整
		calculate();
		sum();    //自动计算总金额
	})
}

//删除选中行的方法
function deleteRow(rowIndex){
	$.messager.confirm('提示信息','你确定要删除改行吗？',function(value){
		if(value){
			$('#grid').datagrid('deleteRow',rowIndex);
		}
	});
}

//统计所以行金额的总和
function sum(){
	//获得所以行数据
	var rowData = $('#grid').datagrid('getRows');
	//定义变量totalMoney
	var totalMoney=0;
	//遍历rowData
	for(var i=0;i<rowData.length;i++){
		totalMoney += parseInt(rowData[i].money);
	}
	//把得到的金额总和写入文本框中
	$('#sumMoney').html(totalMoney);
}

//提交订单的方法
function submitOrder(){
	//关闭处于编辑状态的行
	$('#grid').datagrid('endEdit',nowEditRowIndex);
	//获得form表单中的供应商数据
	var formdata = getFormData('orderForm');
	//获取表格数据，并把其转换为json数据，作为formdata变量的一个属性存储在formdata变量中（如果该属性不存在，会自动创建）
	formdata['json'] = JSON.stringify( $('#grid').datagrid('getRows'));
	
	$.ajax({
		url:'orders_add.action?t.orderType='+Request['type'],
		data:formdata,
		type:'post',
		success:function(value){
			if(value=='ok'){
				$.messager.alert("提示","保存成功！！");
				//提示保存成功的信息，并将表格置空
				$('#grid').datagrid('loadData',{total:0,rows:[]});
				//把订单金额总和置空
				$('#sumMoney').html("0");
			}else{
				$.messager.alert("提示",value);
			}
		}
	});
}
