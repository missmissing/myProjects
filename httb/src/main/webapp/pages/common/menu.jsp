<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	//初始化
	$(function() {
		$("#admin_left ul li ul li").click(function() {
			 $("#admin_left ul li ul li").removeClass("selected").addClass("");
			 $(this).addClass("selected");
			 var text = $(this).children("a").text();//
			 $("#dhtInfo").text(text);
		});
	});
	/** 加载工作区页面 */
	function loadPage(url) {
		//二级导航条参数变更
		var clickVal = "loadPage('"+url+"');"
		$("#dhtInfo").attr("onclick",clickVal);
		//跳转URL
		var openurl = "<c:url value='/'/>" + url;
		openIframePage("",openurl);
	}
</script>
<div id="admin_left">
	<ul class="submenu">
		<li><span>章节管理</span>
			<ul >
				<li class="" value="dsfsd" ><a href="javascript:void(0)" onclick="loadPage('category/list.html')">章节知识点维护</a></li>
			</ul>
		</li>
		<li><span>试题管理</span>
			<ul >
				<li class=""><a href="javascript:void(0)" onclick="loadPage('question/list.html')">试题列表11</a></li>
				<li class=""><a href="javascript:void(0)" onclick="loadPage('question/add.html')">试题添加</a></li>
			</ul>
		</li>
		<li><span>试卷管理</span>
			<ul >
				<li class=""><a href="javascript:void(0)" onclick="loadPage('paper/list.html')">试卷列表</a></li>
				<li class=""><a href="javascript:void(0)" onclick="loadPage('paper/add.html')">试卷添加</a></li>
			</ul>
		</li>
		<li><span>文件管理</span>
			<ul >
				<li class=""><a href="javascript:void(0)" onclick="loadPage('imagemanage/list.html')">图片维护</a></li>
			</ul>
		</li>
		<li><span>系统管理</span>
			<ul >
				<li class=""><a href="javascript:void(0)" onclick="loadPage('question/list.html')">用户管理</a></li>
				<li class=""><a href="javascript:void(0)" onclick="loadPage('question/list.html')">角色管理</a></li>
				<li class=""><a href="javascript:void(0)" onclick="loadPage('question/list.html')">菜单管理</a></li>
			</ul>
		</li>
<%-- 			<app:menuTag menus="${menus}"/> --%>

	</ul>
</div>