<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 修改共享题干试题-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>${TITLE }</title>
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
						<th >题干：</th>
						<td>
							${question.qcontent }
						</td>
					</tr>
					<tr>
						<th style="vertical-align:top;">子题集合： </th>
						<td id="zishitiDiv">
							<!-- 以下样式部分用作定位  勿删除 ************************ -->
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
										<c:forEach items="${child.qcchoiceList}" var="choice" varStatus="status">
											<tr>
												<th>
												选项${status.count}:
												</th>
												<td class="xcca">${choice}</td>
											</tr>
								    	</c:forEach>
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
