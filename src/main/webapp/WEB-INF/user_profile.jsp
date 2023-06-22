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
<title> <c:out value="${userModel.getUserName()}"/> | Profile </title>
<link rel="stylesheet" href="../../../css/style.css">
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
	
	
	
	<!-- Profile Page Content -->
	<main class="main-content-logged">
		
		<!-- User Information -->
		<div class="flex-row spc-bet margin-bot-lgr">
			<div>
				<c:choose>
					<c:when test="${userModel.getUserData() == null}">
						<ul>
							<li>Username: <c:out value="${userModel.getUserName()}"/></li>
							<li>Joined at: <c:out value="${userModel.getCreatedAt()}"/></li>
						</ul>
					</c:when>
					
					<c:otherwise>
						<ul class="user-info">
							<li><img src="../../../img/default-img.png" alt="Default profile picture"></li>
							<li><p class="profile-page-username"><c:out value="${userModel.getUserName()}"/></p></li>
							<li><i class="profile-page-joindate">joined at: <c:out value="${userModel.getCreatedAt()}"/></i></li>
							<li><c:out value="${userModel.getUserData().getFirstName()} ${userModel.getUserData().getLastName()}"/></li>
							<li><c:out value="${userModel.getUserData().getLocation()}"/></li>
							<li>Technology/Language of Expertise:&nbsp;<c:out value="${userModel.getUserData().getProgrammingLanguage()}"/></li>
							
						</ul>
					</c:otherwise> 
				</c:choose>
			
				<c:if test="${currentUser.getUserName().equals(userModel.getUserName())}">
					<form action="/update/user/profile/id/${currentUser.getId()}" method="GET">
						<input id="edit-profile-btn" class="btn-primary" type="submit" value="Edit profile">
					</form>
				</c:if>
			</div>

			<div>
				<a href="/"><< back to Dashboard</a>
			</div>

		</div>

		<!-- Thread List -->
		<div>
			<c:if test="${currentUser.getUserName().equals(userModel.getUserName())}">	
				<h2 class="margin-bot">My thread (${userModel.getThread().size()}):</h2>
			</c:if>
			
			<c:if test="${!currentUser.getUserName().equals(userModel.getUserName())}">	
				<h2 class="margin-bot"> <c:out value="${userModel.getUserName()}"/>'s threads (${userModel.getThread().size()}):</h2>
			</c:if>
				
					<!-- unlooped object for conditional displays .isEmpty() method available to invoke -->
					<c:choose>
						<c:when test="${!userThread.isEmpty()}">
							<c:forEach var="userThread" items="${userThread}">
								<!-- URL VARIABLE -->
								<c:set var="mainTopic_origin" value="${userThread.getForumSubTopic().getForumMainTopics().getTitle()}"/> 
								<c:set var="subTopic_origin" value="${userThread.getForumSubTopic().getTitle()}"/>
								<ul class="margin-bot">
									 <li> <p class="profile-topic-path">[ ${mainTopic_origin} &gt; ${subTopic_origin} ]</p> </li>
									 <li class="margin-bot"> 
										${userModel.getUserName()} posted a thread in ${subTopic_origin}: <p><a href="/forums/${mainTopic_origin}/${subTopic_origin}/thread/${userThread.getId()}">"${userThread.getTitle()}"</a></p> 
									 </li>
									 <hr>
								</ul>	 
							</c:forEach>
						</c:when>
						
						<c:otherwise>
							<li>No data found</li>
						</c:otherwise>
					</c:choose>
				
		</div>	 
		
		<div>	
			<h2 class="margin-bot">Activity Log (${userModel.getTopicComment().size()}):</h2>
				
				<!-- unlooped object for conditional displays .isEmpty() method available to invoke -->
					<c:choose>
						<c:when test="${!userComments.isEmpty()}">
							<c:forEach var="userComments" items="${userComments}">
								<!-- URL VARIABLE -->
								<c:set var="mainTopic_origin" value="${userComments.getThreadTopic().getForumSubTopic().getForumMainTopics().getTitle()}"/> 
								<c:set var="subTopic_origin" value="${userComments.getThreadTopic().getForumSubTopic().getTitle()}"/>
								<c:set var="thread_origin_id" value="${userComments.getThreadTopic().getId()}"/>
								<c:set var="thread_origin_title" value="${userComments.getThreadTopic().getTitle()}"/>  
								<ul class="margin-bot">
									 <li><p class="profile-topic-path">[ ${mainTopic_origin} &gt; ${subTopic_origin} ]</p> </li>
									 <li>
										 <p id="profile-topic-threadTitle">${userModel.getUserName()} commented on: <a href="/forums/${mainTopic_origin}/${subTopic_origin}/thread/${thread_origin_id}"> <c:out value="${thread_origin_title}"/></a></p>
									 </li>
									 <li class="margin-bot"><p id="profile-topic-threadComment"><i><c:out value="${userComments.getComment()}"/></i></p> </li>
									 <hr>
								</ul>
							</c:forEach>
						</c:when>
						
						<c:otherwise>
							<li>No data found</li>
						</c:otherwise>
					</c:choose>
				
		</div>
		
		

		</div>
</main>




	<!-- Link JavaScript File -->
	<script src="../../../js/app.js"></script>

</body>
</html>