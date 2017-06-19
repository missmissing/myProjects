<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 修改共享选项-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${TITLE }</title>
<%@ include file="/common/common.jsp"%>
<%@ include file="/common/common.jsp"%>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.popup/dhtmlxcommon.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.windows/dhtmlxwindows.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/dhtmlx.windows/dhtmlxcontainer.js' />"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/web-resource/scripts/dhtmlx.windows/dhtmlxwindows.css' />" media="all" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/web-resource/scripts/dhtmlx.windows/dhtmlxwindows_dhx_terrace.css' />" media="all" />
	<div class="content_box">
		<div class="content form_content" style="height: 380px;overflow-y: scroll;">
			<form id="questionForm" method="post" accept-charset="utf-8" enctype="multipart/form-data">
				<div style="display: none">
					<input type="hidden" name="dilicms_csrf_token" value="" />
				</div>
				<table class="form_table">
				     <col width="150px" />
					<col />
					<tr>
						<th>地区：</th>
						<td>
					        <c:forEach items="${areaMap}" var="type" varStatus="status">
								<c:if test="${type.key == question.qarea}">${type.value}</c:if>
						    </c:forEach>
						</td>
					</tr>
					<tr>
						<th>年份：</th>
						<td>
							${question.qyear}
						</td>
					</tr>
					<tr>
						<th>属性：</th>
						<td>
		                    	<c:forEach items="${attributeMap}" var="type" varStatus="status">
									<c:if test="${type.key == question.qattribute}">${type.value}</c:if>
							    </c:forEach>
						</td>
					</tr>
					<tr>
						<th>难度：</th>
						<td>
						        <c:forEach items="${difficultyMap}" var="type" varStatus="status">
									<c:if test="${type.key == question.qdifficulty}">${type.value}</c:if>
							    </c:forEach>
						</td>
					</tr>
					<tr>
						<th>知识点：</th>
						<td>
							${qpointnames }
						</td>
					</tr>
					<tr>
						<th style="vertical-align:top;" >选项： </th>
						<td id="optionDiv">
							<c:forEach items="${optionList}" var="myoption" varStatus="status">
								<ht><span class="badge" style="margin-right: 10px;">${status.count }</span>${myoption}<br/></ht>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th style="vertical-align:top;">子题集合：</th>
						<td id="zishitiDiv">
							<c:forEach items="${question.qchild}" var="child" varStatus="status">
								<table class="istablestyle">
									<tr>
										 <td></td>
										 <td class="childTitle">问题${status.count }
										 </td>
									<tr>
										<tr>
											<th>子题干：</th>
											<td class="ztgca">${child.qccontent}</td>
										</tr>
								    	<tr>
											<th >答案：</th>
											<td class="daca">${child.qcans}</td>
										</tr>
										<tr>
											<th >解析：</th>
											<td class="jxca">${child.qccomment}</td>
										</tr>
										<tr>
											<th >拓展：</th>
											<td class="tzca">${child.qcextension}</td>
										</tr>
										<tr>
											<th >分值：</th>
											<td class="fzca">${child.qcscore}</td>
										</tr>
								</table>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th >释义：</th>
						<td>
							${question.qparaphrase }
						</td>
					</tr>
					<tr>
						<th >信息源：</th>
						<td>
							${question.qfrom }
						</td>
					</tr>
					<tr>
						<th >作者：</th>
						<td>
							${question.qauthor }
						</td>
					</tr>
					<tr>
						<th >讨论：</th>
						<td>
							${question.qdiscussion }
						</td>
					</tr>
					<tr>
						<th >技巧：</th>
						<td>
							${question.qskill }
						</td>
					</tr>
					<tr>
						<th >视频：</th>
						<td>
							${question.qvideourl }
						</td>
					</tr>
					<tr style="height: 100px;"></tr>
				</table>
			</form>
		</div>
	</div>