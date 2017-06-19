<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.huatu.sys.session.HtSessionManager"%>
<%@page import="com.huatu.sys.session.HtSession"%>
<%@page import="java.util.*"%>
<%@page import="com.huatu.core.util.ServerContextUtil"%>
<%@page import="com.huatu.core.util.SpringContextHolder"%>
<%@page import="com.huatu.ou.user.model.*" %>
<%@page import="com.huatu.core.util.*" %>

<%
	//new ServerContextUtil().test(request, response);
    User user = null;
    String name = "";
	HtSessionManager htSessionManager =SpringContextHolder.getBean("htSessionManager");
	HtSession htsession = htSessionManager.getSession(request,response);
	if(htsession!=null){
       user = (User)htsession.getAttribute(Constants.SESSION_CURRENT_USER);
       name = user.getUname();
	}
%>

<div id="header">
	<div class="logo">
	</div>
	<div id="menu">
		<ul name="menu">
				<li class=" selected"><a href="javascript:void(0);">题库管理后台</a></li>
<!-- 			<li class="first "><a href="">首页</a></li> -->
<!-- 			<li class=" selected"><a href="">内容管理</a></li> -->
<!-- 			<li class=""><a href="">试题管理</a></li> -->
		</ul>
	</div>
	<p>
		<a href="#" onClick="logOut();">注销</a>
		<!-- 
		<a href="">后台首页</a>
		<a href="" target='_blank'>站点首页</a>
		 -->
		<span>您好 <label class='bold'><%=name %></label></span>
	</p>
</div>
<div id="info_bar">
	<span class="nav_sec"></span>
</div>
