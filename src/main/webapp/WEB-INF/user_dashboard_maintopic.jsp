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
	<nav>
		<h1> Hello, <a href="/user/profile/${currentUser.getUserName()}/"><c:out value="${currentUser.getUserName()}"/></a></h1>
		<form id="logoutForm" method="POST" action="/logout">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <input type="submit" value="Logout!" />
    	</form>
    </nav>
	
	<div id="main-forum-div">
		<div class="main-topic-div">
			<div class="main-header">
				<div class="main-title">
					<c:forEach var="forumMainTopic" items="${forumMainTopic}">
					<ul>	
						<li><a href="/forums/${forumMainTopic.getTitle()}"><c:out value="${forumMainTopic.getTitle()}"/></a></li>
						<li>- <c:out value="${forumMainTopic.getDescription()}"/></li>
					</ul>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	
		
</body>
</html>