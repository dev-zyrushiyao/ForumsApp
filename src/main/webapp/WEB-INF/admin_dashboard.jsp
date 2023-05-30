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
<title>Dashboard</title>
 <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
 <link rel ="stylesheet" type="text/css" href="/css/dashboard-style.css">

<!-- GOOGLE API FONT -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>

<div id="sticky-header">
	<div id="header-flexbox">
		<h1 id="user-greeting">Welcome, Admin <span id="user"><c:out value="${currentUser.getUserName()}"/></span></h1>
		
		<a id="new-game-link" href="/admin/create/main/topic"> Add Main Topic </a>
		<br>
		<a id="new-game-link" href="/admin/view/main/topic"> View Main Topic List </a>
			
		<form id="logoutForm" method="POST" action="/logout">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <input type="submit" value="Logout!" />
    	</form>
	</div>
</div>
		
</body>
</html>