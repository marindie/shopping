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
	//Simple Map Sample
	var map = new Map();
	map.put("id","tt");
//	alert(map.get("id"));
	var anotherMap = new Map();
	anotherMap.put("id","dd");
//	alert(anotherMap.get("id"));
	
	//Simple XML Sample
	var xml = '<root></root>';
	xmlDoc = $.parseXML(xml);
	var str = xmlDoc.createElement("apple");
	$(xmlDoc).find("root").append(str);
	var root = $(xmlDoc).find("root");
//	$(root).innerHTML("hi");
//	var xmlString = (new XMLSerializer()).serializeToString(xmlDoc);
	
	//sample = $(xmlDoc).find("sample");
//	alert(xmlString);
	
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

	//Ajax Form XML Test
	$("#ajaxXmlSample").click(function(){
		var params = makeXmlData();
		$.ajax({
			type : "POST",
			url : "test/ajaxXmlSample",
			data : params,
			//dataType : "xml",
			contentType : "application/xml",
			success : function(data){
				$("#ajaxXmlResponse").text(data);
			},
			error : function(error){
				alert(error);
			}
		});
	});	
	
	//Ajax Form Json Test
	$("#ajaxPostFormJson").click(function(){
		var data = $("#jsonForm").serialize();
		var jsonArray = [];
		
		for(var i=1; i<=2 ; i++){
			var jsonObject = new Object();
			jsonObject.number = i;
			jsonArray.push(data);
		}
		
		var jsonData = JSON.stringify(jsonArray);
		alert(jsonData);
		$.ajax({
			type : "POST",
			url : "/main/test/ajaxPostFormJson",
			cache : false,
			data : jsonData,
			contentType : "application/json",
			success : function(data){
				$("#ajaxJsonResponse").text(data);
			},
			error : function(error){
				alert(error);
			}
		});
	});
	
	makeXmlData = function(data) {
		var xmlString = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><SYNC_REQUEST></SYNC_REQUEST>';
		var parser = new DOMParser();
		var xmlDoc = parser.parseFromString(xmlString, "text/xml"); //important to use "text/xml"
		var elements = xmlDoc.getElementsByTagName("SYNC_REQUEST");

		var regex = /[?&]([^=#]+)=([^&#]*)/g,
	    url = $("#xmlForm").serialize(),
	    params = {},
	    match;
		
		while(match = regex.exec(url)) {
		    params[match[1]] = match[2];
		    var node = xmlDoc.createElement(match[1]);
		    node.innerHTML = match[2];
		    elements[0].appendChild(node);
		}
	var serializer = new XMLSerializer();
	var xmlString = serializer.serializeToString(xmlDoc);
	console.log("xml--------->" + xmlString);
	return xmlString; 
	}	
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
<span id="ajaxXmlResponse">result</span>

<div>
	<form id="jsonForm" action="" method="post">
		jsonData1 <input type="text" name="text1" />
		jsonData2 <input type="text" name="text2" />
		<textArea name="textArea1">simpl json data</textArea>
		<input type="button" id="ajaxPostFormJson" value="ajaxPostFormJson"/>
	</form>
</div>
<span id="ajaxJsonResponse">result</span>
</body>
</html>