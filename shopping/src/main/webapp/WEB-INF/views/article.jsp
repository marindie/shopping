<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<caption>환영합니다.</caption>
	<!-- 
	<c:forEach var="item" items="${data}" varStatus="status">
		<li>${item.id }</li>
		<li>${item.pwd }</li>
	</c:forEach>
	 -->
	 <form method="POST" action="uploadMultipleFile" enctype="multipart/form-data">
		File1 to upload: <input type="file" name="file">
		Name1: <input type="text" name="name">
 		<br>
		File2 to upload: <input type="file" name="file">
		Name2: <input type="text" name="name">
		<br>
		<input type="submit" value="Upload"> Press here to upload the file!
	</form>
</body>
</html>