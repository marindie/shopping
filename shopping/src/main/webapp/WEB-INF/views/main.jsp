<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
	<title>Wony Shopping Mall</title>
</head>
<body>
<div>
	<h1>
		Please Login.
	</h1>
	<form:form action="login" method="POST">
		<form:label path="id">USER ID</form:label>
		<form:input path="id"/>
		<form:label path="pwd">Password</form:label>
		<form:input path="pwd"/>
		<input type="submit" value="submit"/>
	</form:form>
	<form:form action="selectAllCust" method="POST">
		<input type="submit" value="submit" />
	</form:form>
	<a href="/main/test">Go to Test</a>
	<a href="/main/article">Go to Upload</a>
</div>

<P>  The time on the server is ${serverTime}. </P>
</body>
</html>
