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
<title> Complete your profile</title>
</head>
<body>
	
	<a href="/dashboard">Go back</a>
	
	<!-- Profile Info -->
	<c:choose>
		<c:when test="${userModel.getUserData() == null}">
			<ul>
				<li>Username: <c:out value="${userModel.getUserName()}"/></li>
				<li>Joined at: <c:out value="${userModel.getCreatedAt()}"/></li>
			</ul>
		</c:when>
		
		<c:otherwise>
			<ul>
				<!-- <li>USER IMAGE PLACE HOLDER</li> -->
				<li>Username: <c:out value="${userModel.getUserName()}"/></li>
				<li>Name: <c:out value="${userModel.getUserData().getFirstName()} ${userModel.getUserData().getLastName()}"/></li>
				<li>Location: <c:out value="${userModel.getUserData().getLocation()}"/></li>
				<li>Fav. Programming Language <c:out value="${userModel.getUserData().getProgrammingLanguage()}"/></li>
				<li>Joined at: <c:out value="${userModel.getCreatedAt()}"/></li>
				
			</ul>
		</c:otherwise> 
	</c:choose>
	
		<c:if test="${currentUser.getUserName().equals(userModel.getUserName())}">
			<form action="/update/user/profile/id/${currentUser.getId()}" method="GET">
				<input type="submit" value="Update profile">
			</form>
		</c:if>
		
	
		 
	
	
</body>
</html>