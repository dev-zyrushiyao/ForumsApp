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
<title> Update Comment | Dojo Dev Forum  </title>
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
	
	
	<a href="/forums/${forumMainTopic.getTitle()}/${forumSubTopic.getTitle()}/thread/${threadModel.getId()}">GO BACK</a>
	<h3>Edit comment ID: <c:out value="${updateReplyForm.getId()}"/> </h3>
	
	<form:form action="/admin/forums/${forumMainTopic.getTitle()}/${forumSubTopic.getTitle()}/thread/${threadModel.getId()}/update/info/reply/${commentModel.getId()}" method="POST" modelAttribute="updateReplyForm">
		<input type="hidden" name="_method" value="PUT">
		
		<label>Comment:</label>
		<form:input type="text" path="comment" placeholder="${commentModel.getComment()}"/>
		<br>
		<form:errors path="comment" class="text-danger" style="color:red"/>
		
		<!-- TO BE HIDDEN -->
		<form:input type="text" path="threadTopic" readonly="true"/>
		<form:input type="text" path="userAccount" readonly="true"/>
		
		<input type="submit" value="UPDATE">
	</form:form>
		
	
</body>
</html>