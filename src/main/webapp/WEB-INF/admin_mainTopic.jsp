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
<link rel="stylesheet" href="../../../css/style.css">
<title>Create Main Topic</title>
</head>
<body>
	<!-- Header when logged in -->
	<header class="main-header flex-row spc-bet">
		<div>
			<h1 class="main-header-title font-color-primary">&lt; Dojo Dev Forum &gt;</h1>
		</div>
		<!-- Profile Header Section -->
		<div class="flex-row flex-centered dropdown">
			<img id="profile-pic" src="../../../img/default-img.png" alt="Default profile picture">
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

		<div class="flex-row spc-bet">

		<div>
		<h2 class="margin-bot">Create Main Topic</h2>

		<form:form action="/admin/create/new/main/topic" method="POST" modelAttribute="mainTopicForm">
			
			<label style="color:green"><c:out value="${mainTopicMessage}"></c:out></label>

		<ul>
				<li>
					<label>Title: </label>
					<p><form:input class="text-input input-text-pri blk-border margin-y-sm input-field-res" path="title" type="text"/></p>
					
					<form:errors path="title" class="text-danger" style="color:red"/>
					
				</li>
				<li>
					<label>Description: </label>
					<p><form:input class="text-input input-text-pri blk-border margin-y-sm input-field-res" path="description" type="text"/></p>
					
					<form:errors path="description" class="text-danger" style="color:red"/> 
				</li>
				<li>
					<input class="btn-primary margin-bot" type="submit" value="Add">
					<!-- <input type="reset" value="Clear"> -->
				</li>
			</ul>

</form:form>

<a href="/admin/view/main/topic"><< Back to Main Topic List</a>


</div>
</div>
</main>

<!-- Link JavaScript File -->
<script src="../../../js/app.js"></script>

</body>
</html>