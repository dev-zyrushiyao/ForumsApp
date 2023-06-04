<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- JSTL Tag import -->
<%@taglib prefix= "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Data Binding -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- BindingResult -->
<%@ page isErrorPage="true" %> 

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title> <c:out value="${threadModel.getTitle()}"/> | Dojo Dev Forum  </title>
 <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
 <link rel ="stylesheet" type="text/css" href="/css/dashboard-style.css">

<!-- GOOGLE API FONT -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>

	<nav>
		<h1> Hello, <a href="/user/profile/${currentUser.getUserName()}/"><c:out value="${currentUser.getUserName()}"/></a></h1>
		<form id="logoutForm" method="POST" action="/logout">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <input type="submit" value="Logout!" />
    	</form>
    </nav>
		
		 <h1><c:out value="${threadModel.getTitle()}"/></h1>
		<p>
			<a href="/user/profile/${threadModel.getUserThread().getUserName()}"><c:out value="${threadModel.getUserThread().getUserName()}"/></a>
			<br>
			<c:out value="${threadModel.getCreatedAt()}"/>
			<br>
			<c:forEach var="userRole" items="${userRole}">
				<c:out value="${userRole.getName()}"/>
			</c:forEach>
		<p>
		<p> <c:out value="${threadModel.getContent()}"/> </p> 
	
</body>
</html>