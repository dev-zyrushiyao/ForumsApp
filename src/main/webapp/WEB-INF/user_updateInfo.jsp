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
<title><c:out value="${currentUser.getUserName()}"/> | Update Info</title>
<link rel="stylesheet" href="../../../../css/style.css">
</head>
<body>

	<!-- Header when logged in -->
	<header class="main-header flex-row spc-bet">
		<div>
			<h1 class="main-header-title font-color-primary">Dojo Dev Forums</h1>
		</div>
		<!-- Profile Header Section -->
		<div class="flex-row flex-centered dropdown">
			<img id="profile-pic" src="../../../../img/default-img.png" alt="Default profile picture">
			<p class="header-profile-name font-color-primary"><c:out value="${currentUser.getUserName()}"/>&nbsp;&nbsp;<span class="caret-down">&#9660;</span></p>
			
			<!-- Dropdown Content Section -->
			<div class="dropdown-content">
				<a class="dropdown-menu" href="/user/profile/${currentUser.getUserName()}/">View Profile</a>
				<a class="dropdown-menu" href="/update/user/profile/id/${currentUser.getId()}">Edit Profile</a>
				<form id="logoutForm" method="POST" action="/logout">
					<a class="dropdown-menu logout">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<input id="logout-btn" type="submit" value="Logout!" />
					</a>
				</form>
			</div>
		</div>
	</header>


	<main class="main-content-logged">

		<c:if test="${currentUser.getUserData() != null}">
			<form:form action="/update/user/info/${currentUser.getUserData().getId()}" method="POST" modelAttribute="userDataUpdateForm">
				<input type="hidden" name="_method" value="put">
			
				<h2>Update your profile</h2>
				<ul>
					<li>
						<label>First Name: </label>
						<form:input path="firstName" type="text"/>
						<form:errors path="firstName" class="text-danger" style="color:red"/>
						
					</li>
					<li>
						<label>Last Name: </label>
						<form:input path="lastName" type="text"/>
						<form:errors path="lastName" class="text-danger" style="color:red"/> 
					</li>
					<li>
						<label>Location: </label>
						<form:input path="location" type="text"/>
						<form:errors path="location" class="text-danger" style="color:red"/>
					</li>
					<li>
						<label>Favorite Programming Language</label>
						<form:input path="programmingLanguage" type="text"/>
						<form:errors path="programmingLanguage" class="text-danger" style="color:red"/>
					</li>
					<!-- <li> -->
						<!-- to be hidden -->
						<!-- <label>User ID</label>  -->
						<form:input path="userAccount" type="text" hidden="true"/> 
					<!-- </li>  -->
					<li>
						<input type="submit" value="UPDATE INFORMATION">
						<input type="reset" value="Clear">
					</li>
				</ul>
				</form:form>
			</c:if>
			
			<c:if test="${currentUser.getUserData() == null}">
		 		<form:form action="/update/user/add/info/${currentUser.getId()}" method="POST" modelAttribute="userDataForm">
				<input type="hidden" name="_method" value="put">

				<label>Update your profile</label>
				<ul>
					<li>
						<label>First Name:</label>
						<form:input path="firstName" type="text"/>
						<form:errors path="firstName" class="text-danger" style="color:red"/>
						
					</li>
					<li>
						<label>Last Name: </label>
						<form:input path="lastName" type="text"/>
						<form:errors path="lastName" class="text-danger" style="color:red"/> 
					</li>
					<li>
						<label>Location: </label>
						<form:input path="location" type="text"/>
						<form:errors path="location" class="text-danger" style="color:red"/>
					</li>
					<li>
						<label>Favorite Prog. Language</label>
						<form:input path="programmingLanguage" type="text"/>
						<form:errors path="programmingLanguage" class="text-danger" style="color:red"/>
					</li>

					<li>
						<!-- to be hidden -->
						<!-- <label>User ID</label>  -->
						<form:input path="userAccount" type="text" value="${currentUser.getId()}" hidden="true"/> 
					
					</li> 
					<li>
						<input type="submit" value="UPDATE INFORMATION">
						<input type="reset" value="Clear">
					</li>
				</ul>
				</form:form> 
			</c:if>
			
			<a href="/user/profile/${currentUser.getUserName()}">GO BACK</a>
	
		</main>


			<!-- Link JavaScript File -->
			<script src="../../../../js/app.js"></script>
</body>
</html>