<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.huatu.sys.session.HtSessionManager"%>
<%@page import="com.huatu.sys.session.HtSession"%>
<%@page import="java.util.*"%>
<%@page import="com.huatu.core.util.ServerContextUtil"%>
<%@page import="com.huatu.core.util.SpringContextHolder"%>
<%@page import="com.huatu.ou.user.model.*" %>
<%@page import="com.huatu.core.util.*" %>


<style>
.main-one-box{

}
.main-one-login{
	background:#eee;
	height:100%;
	padding:0 0 40px 0;
}
.main-one-title{
	border:1px solid #CDCDCD;
	line-height:30px;
	padding:5px 10px;
}
.main-one-title>h5{
	line-height: 30px;
}
.main-one-content{
	border:1px solid;
	border-color:#CDCDCD;
	border-top:none;
	padding:5px 10px;
	background-color:#f9f9f9;
	font-size:1.3em;
	height:160px;
	line-height:160px;
	text-align:center;
}
</style>
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
<div class="main-one" style="padding:10px; height: 100%;">
<div class="main-one-box">
<div class="main-one-title">
	<h5>欢迎信息</h5>
</div>
	<div class="main-one-content">
	 欢迎  <%=name %> 登陆题库管理后台!
	</div>
</div>
</div>
