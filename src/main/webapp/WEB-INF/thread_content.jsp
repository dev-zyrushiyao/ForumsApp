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
<title>Dojo Dev Forum | <c:out value="${threadModel.getTitle()}"/></title>
 <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
 <link rel ="stylesheet" type="text/css" href="/css/dashboard-style.css">

<!-- GOOGLE API FONT -->
<!-- <link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;500;600;700&display=swap" rel="stylesheet"> -->

<link rel="stylesheet" href="../../../../../css/style.css">

</head>
<body>
	
	<!-- Header when logged in -->
	<header class="main-header flex-row spc-bet">
		<div>
			<h1 class="main-header-title font-color-primary">Dojo Dev Forums</h1>
		</div>
		<!-- Profile Header Section -->
		<div class="flex-row flex-centered dropdown">
			<img id="profile-pic" src="../../../../../img/default-img.png" alt="Default profile picture">
			<p class="header-profile-name font-color-primary"><c:out value="${currentUser.getUserName()}"/>&nbsp;&nbsp;<span class="caret-down">&#9660;</span></p>
			
			
			<!-- Dropdown Content Section -->
			<div class="dropdown-content">
				
				<!-- ADMIN ACCESS ONLY -->
				<c:forEach var="currentUserRole" items="${currentUser.getRoles()}">
					<c:if test="${currentUserRole.getName().equals('ROLE_ADMIN')}">
						<form id="adminForm" method="GET" action="/admin">
							<a class="dropdown-menu-loc logout">
								<input id="adminDash-btn" type="submit" value="Admin Dashboard" />
							</a>
						</form>
					</c:if>
				</c:forEach>

				<!-- DROPDOWN MENU FOR ALL -->
				<a class="dropdown-menu-loc" href="/user/profile/${currentUser.getUserName()}/">View Profile</a>
				<a class="dropdown-menu-loc" href="/update/user/profile/id/${currentUser.getId()}">Edit Profile</a>
				<form id="logoutForm" method="POST" action="/logout">
					<a class="dropdown-menu-loc logout">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<input id="logout-btn" type="submit" value="Logout!" />
					</a>
				</form>
			</div>


		</div>
	</header>


	<main class="main-content-logged">

	<!-- <nav>
		<h1> Hello, <a href="/user/profile/${currentUser.getUserName()}/"><c:out value="${currentUser.getUserName()}"/></a></h1>
		<form id="logoutForm" method="POST" action="/logout">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <input type="submit" value="Logout!" />
    	</form>
    </nav> -->
		
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
					<input type="submit" value="Edit Thread" title="This action is restricted to your account" hidden="true">
				</form>
				
				<form:form action="PLACEHOLDER" method="POST">
					<input type="submit" value="Delete Thread" title="This action is restricted to your account" hidden="true">
				</form:form>
			</c:if>
		</c:forEach>
		
		
		<!-- Thread content -->
		
			<p class="thread-content-info">
				posted by: <a href="/user/profile/${threadModel.getUserThread().getUserName()}"><c:out value="${threadModel.getUserThread().getUserName()}"/></a>
				<br>
				<c:out value="${threadModel.getCreatedAt()}"/>
				<br>
				<c:forEach var="userRole" items="${userRole}">
					<c:out value="${userRole.getName()}"/>
				</c:forEach>
			<p>
			<p class="thread-content-post"> <c:out value="${threadModel.getContent()}"/> </p> 
		
		
		<!-- Thread replies -->
		
			<h5>Comments:</h5>
			<c:forEach var="threadReplies" items="${threadReplies}">
				<ul class="thread-comments">
					<li><a href="/user/profile/${threadReplies.getUserAccount().getUserName()}"><c:out value="${threadReplies.getUserAccount().getUserName()}"/></a> - added a comment</li>
					<li><p class="thread-comment-content"><c:out value="${threadReplies.getComment()}"/></p></li>
				</ul>

				<!-- Edit Delete Comments (Admin) -->
				<c:forEach var="currentUserRole" items="${currentUser.getRoles()}">
					<c:if test="${currentUserRole.getName().equals('ROLE_ADMIN')}">
						<li><form:form method="GET" action="/admin/forums/${forumMainTopic.getTitle()}/${forumSubTopic.getTitle()}/thread/${threadModel.getId()}/update/reply/${threadReplies.getId()}">
								<span><input type="submit" value="Edit comment"></span>
							</form:form> 
						
							<form:form method= "POST" action="/admin/forums/${forumMainTopic.getTitle()}/${forumSubTopic.getTitle()}/thread/${threadModel.getId()}/delete/reply/${threadReplies.getId()}">
								<input type="hidden" name="_method" value="DELETE">
								<span><input type="submit" value="Delete comment" onClick="return confirm('Delete this comment?')"></span>
							</form:form>
						</li>
					</c:if>
				</c:forEach>
				
				<hr>
			</c:forEach> 
		
		</div>
	
		
		
		<!-- thread reply form -->
		<div>
		 <form:form action="/forums/${forumMainTopic.getTitle()}/${forumSubTopic.getTitle()}/thread/new/reply" method="POST" modelAttribute="threadReplyForm">
			<form:textarea class="padding-sm" path="comment" rows="8" cols="75" ></form:textarea>
			<form:errors path="comment" class="text-danger" style="color:red"/>
			<ul>
				<!-- To be hidden -->
				<li>
					<!-- <label>Thread ID:</label> -->
					<form:input type="text" path="threadTopic" value="${threadModel.getId()}" hidden="true"/>
					<input type="text" title="${threadModel.getTitle()}" value="${threadModel.getTitle()}" hidden="true">
				</li>
				
				<li>
					<!-- <label>User ID:</label> -->
					<form:input type="text" path="userAccount" value="${currentUser.getId()}" hidden="true"/>
					<input type="text" value="${currentUser.getUserName()}" hidden="true">
				</li>
			</ul>
			<input type="submit" value="Add comment">
		</form:form> 
	</div>
	</main>
	
</body>
</html>