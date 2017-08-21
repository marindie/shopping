<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script type="text/javascript" src="<c:url value="/resources/jquery-3.2.1.min.js" />"></script> 
<script type="text/javascript" src="<c:url value="/resources/common.js" />"></script>
<script type="text/javascript">
$(document).ready(function(){
	//alert("Test Start");
	//Ajax String Test
	$("#ajaxStringSample").click(function(){
		$.ajax({
			type : "POST",
			url : "ajaxStringSample",
			data : "test",
			success : function(data){
				$("#ajaxStringResponse").val(data);
			}
		});
	});
	//Ajax XML Test
	$("#ajaxXMLSample").click(function(){
		$.ajax({
			type : "POST",
			url : "ajaxXMLSample",
			data : "test",
			dataType : "XML",
			success : function(data){
				$("#ajaxStringResponse").val(data);
			}
		});
	});	
	
	//Simple Map Sample
	var map = new Map();
	map.put("id","tt");
	map.get("id");
	
	//Simple XML Sample
});
</script>
<title>Insert title here</title>
</head>
<body>
this is test
<table>
	<c:forEach items="${listData}" var="list">
		<c:forEach items="${list }" var="map">
				<span>${map.key }</span><br>
				<span>${map.value }</span>	
		</c:forEach>
	</c:forEach>
</table>
<div>
	<span>Ajax Sample</span><button id="ajaxStringSample">Send</button><input id="ajaxStringResponse" />
</div>
</body>
</html>