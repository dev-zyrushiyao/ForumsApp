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
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
 <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
 <link rel ="stylesheet" type="text/css" href="/css/dashboard-style.css">


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

		<div>
			<!-- BREADCRUMB -->
			<h5><a href="/">Dashboard</a> > <a href="/forums/${forumMainTopic.getTitle()}">${forumMainTopic.getTitle()}</a> > <a href="/forums/${forumMainTopic.getTitle()}/${forumSubTopic.getTitle()}/page/0">${forumSubTopic.getTitle()}</a> > Thread#${threadModel.getId()}</h5>

			<div class="flex-row spc-bet">

			<div>
				<h1><c:out value="${threadModel.getTitle()}"/></h1>
			</div>
		 	
		
			<c:forEach var="currentUserRole" items="${currentUser.getRoles()}">
				<!-- Edit Post and Delete Thread -->
				<c:if test="${currentUserRole.getName().equals('ROLE_ADMIN')}">

				<div class="flex-row">

						<div class="edit-btn-cont">
							<form action="/admin/forums/update/thread/id/${threadModel.getId()}" action="GET">
								<button class="mini-btn-lc mini-btn-edit" type="submit"><i class="fa fa-pencil" style="color:rgb(31, 31, 31); font-size: 32px;"></i></button>
								
							</form>
						</div>

						<div class="del-btn-cont">
							<form:form action="/admin/forums/delete/thread/id/${threadModel.getId()}" method="POST">
								<input type="hidden" name="_method" value="DELETE">
								<button class="mini-btn-lc mini-btn-delete" type="submit" onClick="return confirm('Are you sure you want to delete this post? Don\'t say I don\'t warn you.')"><i class="fa fa-trash-o" style="color:red; font-size: 32px;"></i></button>
							</form:form>
						</div>
					
				</div>

				</c:if>
				
				
			</c:forEach>
		
		</div>
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
		
		<h5>Comments (${threadReplies.size()}):</h5>
			<c:forEach var="threadReplies" items="${threadReplies}">

				<div class="flex-row spc-bet">

					<div>
						<ul class="thread-comments">
							<li><a href="/user/profile/${threadReplies.getUserAccount().getUserName()}"><c:out value="${threadReplies.getUserAccount().getUserName()}"/></a> - added a comment <span class="cmt-createdTime">(${threadReplies.getCreatedAt()})</span></li>
							<li><p class="thread-comment-content"><c:out value="${threadReplies.getComment()}"/></p></li>
						</ul>
					</div>


					<!-- Edit Delete Comments Button (Admin) -->
					<div class="comment-admin-btns">

						
						<c:forEach var="currentUserRole" items="${currentUser.getRoles()}">
							<c:if test="${currentUserRole.getName().equals('ROLE_ADMIN')}">
								<div class="flex-row">
									<div class="edit-btn-cont-sm">
										<form:form method="GET" action="/admin/forums/${forumMainTopic.getTitle()}/${forumSubTopic.getTitle()}/thread/${threadModel.getId()}/update/reply/${threadReplies.getId()}">
											<button class="mini-btn-lc-sm mini-btn-edit" type="submit"><i class="fa fa-pencil" style="color:rgb(31, 31, 31); font-size: 16px;"></i></button>
										</form:form> 
									</div>

									<div class="del-btn-cont">
										<form:form method= "POST" action="/admin/forums/${forumMainTopic.getTitle()}/${forumSubTopic.getTitle()}/thread/${threadModel.getId()}/delete/reply/${threadReplies.getId()}">
											<input type="hidden" name="_method" value="DELETE">
											<button class="mini-btn-lc-sm mini-btn-delete" type="submit" onClick="return confirm('Delete this comment?')"><i class="fa fa-trash-o" style="color:red; font-size: 16px;"></i></button>
										</form:form>
									</div>
								</div>
							</c:if>
						</c:forEach>

					</div>

				</div>
				<hr>
			</c:forEach> 
		
		</div>
	
		
		
		<!-- thread reply form -->
		<div>
		 <form:form action="/forums/${forumMainTopic.getTitle()}/${forumSubTopic.getTitle()}/thread/new/reply" method="POST" modelAttribute="threadReplyForm">
			
		 	<!-- COMMENT TEXTAREA -->
		 	<form:textarea class="padding-sm" path="comment" rows="8" cols="75" placeholder="Write a comment..."></form:textarea>
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
			<input class="btn-primary" type="submit" value="Add">
		</form:form> 
	</div>

	
	</main>
	




	<!-- Link JavaScript File -->
	<script src="/js/app.js"></script>
</body>
</html>