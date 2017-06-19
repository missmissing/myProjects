<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="search f_r" style="position: relative">
	<button class="btn" type="button" hidefocus="true" onclick="$('#dili_uploader').toggle('slow');">
		<span class="add">附件列表</span>
	</button>
	<div id="dili_uploader">
		<div class="red_box" style="margin-bottom: 2px;">
<!-- 			<img src="images/error.gif" />上传完附件请注意一定要提交表单，以免附件丢失。 -->
		</div>
		<div style="position: relative; text-align: left;">
			<p style="line-height: 30px;">
				<a style="margin-left: 5px;" id="uploaderSwitcher" onclick="toggleUploader();" href="javascript:void(0)">打开上传控件</a>允许上传的格式:<b>*.jpg;*.gif;*.png;*.doc</b>,大小限制:<b>2.00MB</b>
			</p>
			<div id="uploaderContainer" style="position: absolute; z-index: 3000000; display: none; background: #ccc;">
			</div>
		</div>
		<ul id="attachList">
			<li id="loading"></li>
		</ul>
	</div>
</div>