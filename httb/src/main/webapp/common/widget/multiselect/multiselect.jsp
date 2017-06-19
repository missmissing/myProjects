<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery/jquery-1.7.2.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery.multiselect/jquery.ui.core.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery.multiselect/jquery.ui.widget.js' />"></script>
<script type="text/javascript" src="<c:url value='/web-resource/scripts/jquery.multiselect/jquery.multiselect.js' />"></script>
<link   rel="stylesheet" type="text/css"   href="<c:url value='/web-resource/styles/jquery.multiselect/jquery.multiselect.css' />" />
<link   rel="stylesheet" type="text/css"   href="<c:url value='/web-resource/styles/jquery.multiselect/jquery-ui.css' />" />
<!-- 
<head>
<script type="text/javascript">
$(function() {
	$("select").multiselect({
		   selectedText: "# of # selected"
		});
});

function loadrole(){
	$.ajax({                    
		url : "<c:url value='/role/getroleOpts.action'/>",
		data : "",
		dataType : 'json',
		type : 'POST',
		cache : false,
		success : function(data) {
			if (data.success) {
				var innercontent="<select id='orgselect' name='orgselect' multiple>";
				innercontent+=data.data;
				innercontent+="</select>";
				$("#test").html(innercontent); 
				$("select").multiselect({
					   selectedText: "# of # selected"
					});
				
			} else {
				return false;
			}
		}, 
		error : function(data) {
			alert("error")
			return false;
		},
		async:false
	});
}

</script>
</head>
<body>
<a href="#" onclick="loadrole()" >aaaaaaaa</a>
<div id="test">
	<select id="orgselect" name="orgselect" multiple>
	  		<option value="PHP">PHP</option>
	        <option value="MYSQL">MYSQL</option>
	        <option value="ASP.NET">ASP.NET</option>
	        <option value="XHTML">XHTML</option>
	        <option value="CSS">CSS</option>
	        <option value="JQUERY">JQUERY</option>
	</select>
</div>
</body>
 -->