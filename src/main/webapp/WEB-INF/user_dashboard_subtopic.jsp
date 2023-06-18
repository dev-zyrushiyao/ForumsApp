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
<title> <c:out value="${forumMainTopic.getTitle()}"/> | Dojo Dev Forums</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<header class="main-header">
		<h1 class="main-header-title font-color-primary">Dojo Dev Forums</h1>
	</header>

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
					<c:forEach var="forumSubTopic" items="${forumSubTopic}">
					<ul>	
						<li><a href="/forums/${forumMainTopic.getTitle()}/${forumSubTopic.getTitle()}/page/0"><c:out value="${forumSubTopic.getTitle()}"/></a></li>
						<li>- <c:out value="${forumSubTopic.getDescription()}"/></li>
					</ul>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	
		
	<!-- Link JavaScript File -->
	<script src="/js/app.js"></script>
</body>
</html>