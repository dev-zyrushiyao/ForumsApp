<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix= "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Data Binding -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- BindingResult -->
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dojo Dev Forums</title>
</head>
<body>
	
	<h1> Hello User <a href="/user/profile/${currentUser.getUserName()}/"><c:out value="${currentUser.getUserName()}"/></a></h1>

	<div id="main-forum-div">
		<div class="main-topic-div">
			<div class="main-header">
				<div class="main-title">
					<ul>
						<li><a href="TEST">TITLE PLACEHOLDER</a></li>
						<li>-TITLE DETAILS</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	
		<form id="logoutForm" method="POST" action="/logout">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <input type="submit" value="Logout!" />
    	</form>
</body>
</html>