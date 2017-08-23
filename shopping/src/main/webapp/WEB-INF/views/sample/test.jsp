<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
	$("#ajaxXmlSample").click(function(){
		var xml = "<root></root>",
		xmlDoc = $.parseXML(xml),
		$xml = $(xmlDoc);
		
		var regex="/[?&]([^#])=()/g",
		data = $("#xmlForm").serilize(),
		params ={},
		match;
		while(match = regex.exec(data)){
			params[match[1]] = match[2];
		}
		
		$.ajax({
			type : "POST",
			url : "ajaxXmlSample",
			data : data,
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
	var xml = '<root><sample>This is test</sample></root>', 
	xmlDoc = $.parseXML(xml),
	$xml = $(xmlDoc),
	$sample = $xml.find("sample");
	alert($sample.text());
	
	//AjaxFormSerializePostSample
	$("#ajaxPostFormSample").click(function(){
		var formData = $("#ajaxForm").serialize();
		$.ajax({
			type : "POST",
			url : "/main/test/ajaxPostFormSample",
			cache : false,
			data : formData,
			success : function(data){
				alert(data);
			},
			error : function(error){
				alert(error);
			}
		});
	});
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
	<form name="ajaxForm" id="ajaxForm" action="" method="post">
		<div>
			AjaxSampleText1 = <input type="text" name="textData1" />
			AjaxSampleText2 = <input type="text" name="textData2" />
		</div>
		<div>
			<input type="button" id="formSendBtn" value="AjaxFormPostSample"/>
		</div>
	</form>
	<span>Ajax Sample</span><button id="ajaxStringSample">Send</button><input id="ajaxStringResponse" />
</div>
<div>
	<form id="xmlForm" action="" method="post">
		xmlData1 <input type="text" name="text1" />
		xmlData2 <input type="text" name="text2" />
		<textArea name="textArea1">asdfasdf</textArea>
		<input type="button" id="ajaxXmlSample" value="ajaxXmlSample"/>
	</form>
</div>
</body>
</html>