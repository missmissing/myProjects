<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>${TITLE }</title>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript">
			$(function() {
				var height = $(window).height()*0.77;
				$("#content_ifr").height(height<500?500:height);
			});

			/**
			 *iframe页面跳转调用公共方法
			 *text-第三级菜单显示的内容
			 */
			function openIframePage(text,url){
				$("#dhtchildInfo").text(text);
				document.getElementById("content_ifr").src = url;
			}
			//最大化窗口
			function maxWins(){
				document.body.className = "folden";
				$("#info_bar").hide();
				$("#header").hide();

			}
			//窗口还原
			function minWins(){
				document.body.className = "";
				$("#info_bar").show();
				$("#header").show();

			}
			/** 重新计算iframe高度，做到自适应，适用于页面中有异步处理改变文档高度的情况 */
			function resetIframeHeight() {
				var iframeObj = document.getElementById("content_ifr");
				iframeHeightSet(iframeObj);
			}

			function logOut() {
				$.ajax({
					url : "<c:url value='/login/logOut.action' />",
					data : '',
					dataType : 'json',
					type : 'POST',
					cache : false,
					success : function(data) {
						//alert(data.success);
						if (data.success) {
							location.href="<c:url value='/common/index.jsp'/>";
							//window.navigate("/pages/common/main.jsp");
						} else {
							var meg = data.message;
							if(typeof(meg) =='undefined'){
							// alert("注销过程出错，请联系管理员或重新登录！");
								location.href="<c:url value='/common/index.jsp'/>";
							}else{
								//alert(data.message);
								location.href="<c:url value='/common/index.jsp'/>";
							}
							return false;
						}
					},
					error : function(data) {
						var meg = data.message;
						if(typeof(meg) =='undefined'){
							//alert("注销过程出错，请联系管理员！");
							location.href="<c:url value='/common/index.jsp'/>";
						}else{
							//alert(data.message);
							location.href="<c:url value='/common/index.jsp'/>";
						}
						return false;
					},
					async:false
				});
			}
		</script>
	</head>
	<body class="bg">
		<!-- 头信息 -->
		<jsp:include page="head.jsp" />
		<!-- 左侧菜单-->
		<jsp:include page="menu_tmp.jsp" />
		<!-- main区域   begin-->
		<div id="admin_right">
			<!-- 导航栏-->
			<div class="position">
				<span class="bread_name">首页</span>
				<span class="bread_gt">&gt;</span>
				<span class="bread_name"><a href="###" id="dhtInfo" onclick="" target="_self"></a></span>
				<span class="bread_gt">&gt;</span><span id="dhtchildInfo" class="bread_name"></span>
			</div>
			<iframe style="display: block;width: 100%;height: 100%;" id="content_ifr" name="content_ifr" frameborder="0" scrolling="no"  src="<c:url value='/pages/common/welcome.jsp' />"> </iframe>

		</div>
		<div id="separator"></div>
		<!-- main区域   end-->



	</body>
</html>
