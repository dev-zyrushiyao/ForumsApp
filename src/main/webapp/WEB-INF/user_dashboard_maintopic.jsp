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
	<link rel="stylesheet" href="/css/style.css">
	<link rel="icon" type="image/x-icon" href="/img/favicon.ico">
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
	<title>Dashboard | Dojo Dev Forum</title>
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

			<div class="flex-row flex-centered">
				<div class="margin-rt-lgr">
					<h1 class="margin-bot-smlr">Dashboard</h1>
					<p>Choose your topic:</p>
				</div>
				<div>
					<!-- ADMIN ACCESS ONLY -->
					<c:forEach var="currentUserRole" items="${currentUser.getRoles()}">
						<c:if test="${currentUserRole.getName().equals('ROLE_ADMIN')}">
							<form id="adminForm" method="GET" action="/admin/create/main/topic">
								<a class="dropdown-menu-loc logout">
									<input class="btn-primary" type="submit" value="New Main Topic" />
								</a>
							</form>
						</c:if>
					</c:forEach>
			</div>

			</div>
			
			
			
			<div class="flex-column topic-wrapper">
				
				<c:forEach var="forumMainTopic" items="${forumMainTopic}">
					<!-- TOPIC CONTAINER -->
					<a href="/forums/${forumMainTopic.getTitle()}">
						<div class="topic-cont main-topic-cont">
							
							<p class="cont-main-title-text word-break"><c:out value="${forumMainTopic.getTitle()}"/></p>
							<p class="cont-main-desc-text word-break"><c:out value="${forumMainTopic.getDescription()}"/></p>
							
						</div>
					</a>
				</c:forEach>
					
				



			</div>
		</div>


	</main>

	
	<!-- Link JavaScript File -->
	<script src="/js/app.js"></script>


</body>
</html>