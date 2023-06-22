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
<title>Dojo Dev Forum | New Thread</title>
<link rel="stylesheet" href="../../../../../css/style.css">
</head>
<body>

	<!-- Header when logged in -->
	<header class="main-header flex-row spc-bet">
		<div>
			<h1 class="main-header-title font-color-primary">&lt; Dojo Dev Forum &gt;</h1>
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

<a href="/forums/${forumMainTopic.getTitle()}/${forumSubTopic.getTitle()}/page/0"> GO BACK</a>
<br>
		<label>Create a new discussion thread related to ${forumMainTopic.getTitle()} - ${forumSubTopic.getTitle()}:</label>
		<br>
 		<form:form action="/forums/${forumMainTopic.getTitle()}/${forumSubTopic.getTitle()}/create/topic" method="POST" modelAttribute="threadForm">

			<ul>
				<li>
					<label>Topic Title: </label>
					<form:input path="title" type="text"/>
					<br>
					<form:errors path="title" class="text-danger" style="color:red"/>
				</li>
				<li>
					<label>Post content: </label>
					<form:textarea path="content" type="text"/>
					<br>
					<form:errors path="content" class="text-danger" style="color:red"/> 
				</li>

				<!-- TO BE HIDDEN  -->
				<!-- <li>  -->
					<!-- <label>forumSubTopic ID: </label> -->
					<form:input path="forumSubTopic" type="text" value="${forumSubTopic.getId()}" hidden="true"/>
					<!-- <br>
					<form:errors path="forumSubTopic" class="text-danger" style="color:red"/> -->
				<!-- </li> -->

				<!-- TO BE HIDDEN  -->
				<!-- <li> 
					<label>User Thread ID: </label> -->
					<form:input path="userThread" type="text" value="${currentUser.getId()}" hidden="true"/>
					<!-- <br>
					<form:errors path="userThread" class="text-danger" style="color:red"/>
				</li> -->
				<li>
					<input type="submit" value="Submit">
					<!-- <input type="reset" value="Clear"> -->
				</li>
			</ul>
			

			</form:form>

		</main>






	<!-- Link JavaScript File -->
	<script src="/js/app.js"></script>
</body>
</html>