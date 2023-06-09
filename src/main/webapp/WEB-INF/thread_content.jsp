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
		
		<div>
		 	<h1><c:out value="${threadModel.getTitle()}"/></h1>
		 	
		 	
		<!-- Looped a UserRoleModel List as forEach of the current user ROLE,
		 it only loop 1 element since a single user only has single role;
		 used loop so we can have access to UserRoleModel.getName(), otherwise
		 it will have a conflict on conditional(if-else) 
		 because we can't compare an string('ROLE_ADMIN') to an Object ;
		 Cannot change the UserRole as Optional because its binded with Many To Many Relation(Thus its declared as List)-->
		<c:forEach var="currentUserRole" items="${currentUser.getRoles()}">
			<!-- Edit Post and Delete Thread -->
			<c:if test="${currentUserRole.getName().equals('ROLE_ADMIN')}">
				<form action="/admin/forums/update/thread/id/${threadModel.getId()}" action="GET">
					<input type="submit" value="Edit Thread">
				</form>
				
				<form:form action="/admin/forums/delete/thread/id/${threadModel.getId()}" method="POST">
					<input type="hidden" name="_method" value="DELETE">
					<input type="submit" value="Delete Thread" onClick="return confirm('Are you sure you want to delete this thread?')">
				</form:form>
			</c:if>
			
			<c:if test="${!currentUserRole.getName().equals('ROLE_ADMIN')}">
				<form action="PLACEHOLDER" method="POST">
					<input type="submit" value="Edit Thread" title="This action is restricted to your account" disabled>
				</form>
				
				<form:form action="PLACEHOLDER" method="POST">
					<input type="submit" value="Delete Thread" title="This action is restricted to your account" disabled>
				</form:form>
			</c:if>
		</c:forEach>
		</div>
		
		<!-- Thread content -->
		<div>
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
		</div>
		
		<!-- Thread replies -->
		<div>
			<c:forEach var="threadReplies" items="${threadReplies}">
				<ul>
					<li> <c:out value="${threadReplies.getComment()}"/> </li>
					<li> by <a href="/user/profile/${threadReplies.getUserAccount().getUserName()}"> <c:out value="${threadReplies.getUserAccount().getUserName()}"/> </a> </li>
				</ul>
				<hr>
			</c:forEach> 
		
		</div>
	
		
		
		<!-- thread reply form -->
		 <form:form action="/forums/${forumMainTopic.getTitle()}/${forumSubTopic.getTitle()}/thread/new/reply" method="POST" modelAttribute="threadReplyForm">
			<form:textarea path="comment" rows="10" cols="150" style="resize:none"></form:textarea>
			<form:errors path="comment" class="text-danger" style="color:red"/>
			<ul>
				<li><!-- To be hidden -->
					<label>Thread ID:</label>
					<form:input type="text" path="threadTopic" value="${threadModel.getId()}"/>
					<input type="text" title="${threadModel.getTitle()}" value="${threadModel.getTitle()}">
				</li>
				
				<li>
					<label>User ID:</label>
					<form:input type="text" path="userAccount" value="${currentUser.getId()}"/>
					<input type="text" value="${currentUser.getUserName()}">
				</li>
			</ul>
			<input type="submit" value="POST REPLY">
		</form:form> 
		
	
</body>
</html>