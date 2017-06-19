<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<title></title>
<head>
<!-- 引入公共页面 -->
<%@ include file="/common/common.jsp"%>

 <style type="text/css">
            .divv
            {
                position:absolute;
                top:30%;
                left:30%;
                width:200px;
                height:40px;
                border:1px solid #666;
                background-color:#9CF;
                text-align:center;
                display:none;
                z-index:300;
            }
            .popup
            {
                border:1px solid red;
                position:absolute;
                top:0px;
                left:0px;
                width:100%;
                height:100%;
                background-color:#000;
                filter:alpha(opacity=45);
                opacity:0.45;
                display:none;
                z-index:200;
            }
            .fff
            {
                border:1px solid blue;
                position:relative;
                background-color:#000;
            }
        </style>

        <script type="text/javascript">
            $(document).ready(
                function(){

                    //页面初始化
                    goCenter();

                    //滚动条滚动
                    $(window).scroll(
                        function(){
                            goCenter();
                        }
                    );

                    //拖动浏览器窗口
                    $(window).resize(
                        function(){
                            goCenter();
                        }
                    );

                    //锁屏
                    $("#lockButton").click(
                        function(){
                            $("#popupDiv").show();
                            $("#alertDiv").show();
                        }
                    );

                    //解屏
                    $("#unlockButton").click(
                        function(){
                            $("#popupDiv").hide();
                            $("#alertDiv").hide();
                        }
                    );
                }
            );

            function goCenter()
            {
                var h = $(document).height();
                var w = $(document).width();
                var st = $(window).scrollTop();
                var sl = $(window).scrollLeft();

                $("#popupDiv").css("width", w+sl);
                $("#popupDiv").css("height", h+st);
                $(".divv").css("top", ((h-60)/2)+st);
                $(".divv").css("left", ((w-80)/2)+sl);
            }

            /**进入到试题添加页*/
        	function goback() {
        		var url = "<c:url value='/question/list.html' />";
        		//window.parent.document.getElementById("content_ifr").src = url;
        		parent.openIframePage("",url);
        	}
			/**从缓存同步到数据库**/
        	function funSubmit() {
        		$("#popupDiv").show();
        		$("#alertDiv").show();
        		//批次号
        		var batchnum = $("#batchnum").val();
        		$.ajax({
        			url : "<c:url value='/quesinput/submit2Nosql.action' />",
        			data : {"batchnum":batchnum, "pageFrom":'<%=request.getParameter("pageFrom") %>', "pid":'<%=request.getParameter("pid") %>'},
        			dataType : 'json',
        			type : 'POST',
        			cache : false,
        			success : function(data) {
        				$("#popupDiv").hide();
        				$("#alertDiv").hide();
        				if (data.success) {
        					art.dialog.tips(data.message);
        					if('<%=request.getParameter("pageFrom") %>' == 'paper'){
        						var url = "<c:url value='/paper/list.html' />";
        						parent.openIframePage("",url);
        					}
        				} else {
        					alert(data.message);
        					return false;
        				}
        			},
        			error : function(data) {
        				alert(data.message);
        				return false;
        			}
        		});
        	 }

        	/**清空该批次号缓存**/
        	function funClear() {
        		//批次号
        		var batchnum = $("#batchnum").val();
        		$.ajax({
        			url : "<c:url value='/quesinput/clearCache.action' />",
        			data : {"batchnum":batchnum},
        			dataType : 'json',
        			type : 'POST',
        			cache : false,
        			success : function(data) {
        				if (data.success) {
        					alert(data.message);
        					//清空原内容
    						$("#ques_batchnum_val").text("");
    						$("#ques_result_val").text("");
        				} else {
        					alert(data.message);
        					return false;
        				}
        			},
        			error : function(data) {
        				alert(data.message);
        				return false;
        			},
        			async:false
        		});
        	 }
        </script>
</head>
<body>
	<div class="headbar">
		<div class="operating" style="overflow: visible">
			<a class="hack_ie" href="javascript:void(0);"><button
					class="operating_btn" onclick="goback();" type="button">
					<span>返回列表</span>
				</button></a>
		</div>
	</div>
	<div class="content_box">
		<div class="content form_content">

        <div class="popup" id="popupDiv">
            <!--IE6下，DIV默认不能遮盖select标签，可在此处放置一个iframe，以达到遮盖效果，其他浏览器可以不要-->
            <iframe frameborder="1" scrolling="no" height="100%" width="100%" class="fff"></iframe>
        </div>

        <div class="divv" id="alertDiv">
           <span>正在上传,请您耐心等候……</span>
        </div>
			<form action="/imagemanage/add.action" id="imageForm" method="post" accept-charset="utf-8" enctype="multipart/form-data">
				<div style="display: none">
					<input type="hidden" name="dilicms_csrf_token" value="" />
				</div>
				<table class="form_table">
					<col width="150px" />
					<col />

					<tr>
						<th><div>试题分值：<div/></th>
						<td ><input id="qcscore" type="text" class="normal" style="width:165px;"/>
						</td>
					</tr>
					<tr>
						<th><div>导入试题：<div/></th>
						<td ><%@include file="/common/upload/uploadQuestion.jsp"%>
						</td>
					</tr>
					<tr>
						<th><div id="ques_batchnum_name"></div></th>
						<td >
							<div id="ques_batchnum_val"></div>
						</td>
					</tr>
					<tr>
						<th><div id="ques_result_name"></div></th>
						<td >
							<div id="ques_result_val"></div>
						</td>
					</tr>
					<tr>
						<th><div id="ques_error_name"></div></th>
						<td >
							<div id="ques_error_val"></div>
						</td>
					</tr>
					<tr>
						<th></th>
						<td align="center">
							<div id="ques_opt_val">
								<input type="button" class="submit" style="width:80px;" onclick="funSubmit();" value="提    交"/>
	              				<input type="button" class="submit" style="width:80px;" onclick="funClear();" value="取     消" />
	              			</div>
						</td>
					</tr>
					<tr>
						<th></th>
						<td >
							<div id="ques_input_waiting"></div>
						</td>
					</tr>
				</table>
				<input type="hidden" id="batchnum"/>
			</form>
		</div>
	</div>
</body>
</html>

