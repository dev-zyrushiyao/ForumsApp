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
<title>View All Main Topics</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
<link rel ="stylesheet" type="text/css" href="/css/style.css">
<link rel="icon" type="image/x-icon" href="/img/favicon.ico">
</head>
<body id="bootstrap-overlap">

<!-- Header when logged in -->
<header class="main-header flex-row spc-bet">
	<div>
		<h1 class="main-header-title font-color-primary">&lt; Dojo Dev Forum &gt;</h1>
	</div>
	<!-- Profile Header Section -->
	<div class="flex-row flex-centered dropdown">
		<img id="profile-pic" src="/img/default-img.png" alt="Default profile picture">
		<p class="header-profile-name font-color-primary"><c:out value="${currentUser.getUserName()}"/>&nbsp;&nbsp;<span class="caret-down">&#9660;</span></p>
		
		<!-- Dropdown Content Section -->
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
	<div>
	<a href="/admin">&lt;&lt; back</a>
	
	<h1>Main topics</h1>
		
		
	<table class="table table-hover">
	  <thead>
		<tr>
		  <th scope="ID">#</th>
		  <th scope="col">Title</th>
		  <th scope="col">Description</th>
		  <th scope="col">Action</th>
		</tr>
	  </thead>
	  <tbody>
		  
		  <c:forEach var="topicList" items="${forumMainTopic}">
		<tr>
			  <th scope="row"><c:out value="${topicList.getId()}"/></th>
			  <td><a href="/admin/view/${topicList.getTitle()}/subtopic/" title="View Sub-Topics for ${topicList.getTitle()}"> <c:out value="${topicList.getTitle()}"/> </a> </td>
			  <td> <c:out value="${topicList.getDescription()}"/> </td>
				  <td> 
					  <div>
						  <form action="/admin/update/main/topic/id/${topicList.getId()}" method="GET">
							  <input type="submit" class="btn btn-primary edit-btn-bs" value="Edit">
						  </form>
									  
						<form:form action="/admin/delete/main/topic/id/${topicList.getId()}" method="POST">
							 <input type="hidden" name="_method" value="DELETE"> 
							 <input type="submit" class="btn btn-danger" value="Delete" onClick="return confirm('Are you sure you want to delete [ID:' + ${topicList.getId()} + ']')">
						</form:form>
						  
					  </div>
					  
					  
				  </td>
		</tr>
		</c:forEach> 	
	  
	  </tbody>
	</table>

	
	<form method="GET" action="/admin/create/main/topic">
			<input class="btn btn-primary" type="submit" value="Add a Main Topic" />
	</form>
</div>
</main>



<!-- Link JavaScript File -->
<script src="/js/app.js"></script>
</body>
</html>