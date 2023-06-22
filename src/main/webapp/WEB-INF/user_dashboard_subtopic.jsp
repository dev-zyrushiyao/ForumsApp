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


	<!-- Main Content -->
	<main class="main-content-logged">

		
		<div class="flex-row spc-bet">
			<div>

				<!-- BREADCRUMB -->
				<div class="margin-bot">
					<h2><a href="/">Dashboard</a> > ${forumMainTopic.getTitle()}</h2>
				</div>
				<div class="margin-bot">
					
					<h1>This is the ${forumMainTopic.getTitle()} Topic</h1>
					<p>${forumMainTopic.getDescription()}</p>
					
				</div>
				
				<div class="flex-column topic-wrapper">
					
					<c:forEach var="forumSubTopic" items="${forumSubTopic}">
						
					<a href="/forums/${forumMainTopic.getTitle()}/${forumSubTopic.getTitle()}/page/0">
						<div class="topic-cont sub-topic-cont">
							<p class="cont-sub-title-text"><c:out value="${forumSubTopic.getTitle()}"/></p>
							<p class="cont-sub-desc-text"><c:out value="${forumSubTopic.getDescription()}"/></p>
						</div>
					</a>
						
					</c:forEach>

					<!-- ADMIN ACCESS ONLY -->
					<c:forEach var="currentUserRole" items="${currentUser.getRoles()}">
						<c:if test="${currentUserRole.getName().equals('ROLE_ADMIN')}">
							<form id="adminForm" method="GET" action="/admin/create/${forumMainTopic.getTitle()}/sub/topic">
								<a class="dropdown-menu-loc logout">
									<input id="adminDash-btn" type="submit" value="New Sub Topic" />
								</a>
							</form>
						</c:if>
					</c:forEach>
							
			</div>
			</div>


			
		</div>
	
	</main>
		
	<!-- Link JavaScript File -->
	<script src="/js/app.js"></script>
</body>
</html>