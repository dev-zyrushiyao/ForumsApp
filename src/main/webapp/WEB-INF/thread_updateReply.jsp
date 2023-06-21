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
 <!-- <link rel ="stylesheet" type="text/css" href="/css/dashboard-style.css"> -->
 <link rel="stylesheet" href="../../../../../../../../css/style.css">
<!-- GOOGLE API FONT -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>

	<!-- Header when logged in -->
	<header class="main-header flex-row spc-bet">
		<div>
			<h1 class="main-header-title font-color-primary">Dojo Dev Forums</h1>
		</div>
		<!-- Profile Header Section -->
		<div class="flex-row flex-centered dropdown">
			<img id="profile-pic" src="../../../../../../../../img/default-img.png" alt="Default profile picture">
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
		<p><form:textarea class="padding-sm" type="text" path="comment" rows="8" cols="75"/></p>
		<br>
		<form:errors path="comment" class="text-danger" style="color:red"/>
		
		<!-- TO BE HIDDEN -->
		<form:input type="text" path="threadTopic" hidden="true"/>
		<form:input type="text" path="userAccount" hidden="true"/>
		
		<input type="submit" value="Update Comment">
	</form:form>
		
</main>






	<!-- Link JavaScript File -->
	<script src="/js/app.js"></script>
</body>
</html>