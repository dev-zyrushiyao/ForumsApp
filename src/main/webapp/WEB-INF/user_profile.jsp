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
<title> Complete your profile</title>
</head>
<body>
	
	<a href="/dashboard">Go back</a>
	
	<!-- Profile Info -->
	<c:choose>
		<c:when test="${userModel.getUserData() == null}">
			<ul>
				<li>Username: <c:out value="${userModel.getUserName()}"/></li>
				<li>Joined at: <c:out value="${userModel.getCreatedAt()}"/></li>
			</ul>
		</c:when>
		
		<c:otherwise>
			<ul>
				<!-- <li>USER IMAGE PLACE HOLDER</li> -->
				<li>Username: <c:out value="${userModel.getUserName()}"/></li>
				<li>Name: <c:out value="${userModel.getUserData().getFirstName()} ${userModel.getUserData().getLastName()}"/></li>
				<li>Location: <c:out value="${userModel.getUserData().getLocation()}"/></li>
				<li>Fav. Programming Language <c:out value="${userModel.getUserData().getProgrammingLanguage()}"/></li>
				<li>Joined at: <c:out value="${userModel.getCreatedAt()}"/></li>
				
			</ul>
		</c:otherwise> 
	</c:choose>
	
		<c:if test="${currentUser.getUserName().equals(userModel.getUserName())}">
			<form action="/update/user/profile/id/${currentUser.getId()}" method="GET">
				<input type="submit" value="Update profile">
			</form>
		</c:if>
		
	<div>
		<c:if test="${currentUser.getUserName().equals(userModel.getUserName())}">	
			<h3> My thread </h3>
		</c:if>
		
		<c:if test="${!currentUser.getUserName().equals(userModel.getUserName())}">	
			<h3> <c:out value="${userModel.getUserName()}"/>'s thread </h3>
		</c:if>
			<ul>
				<!-- unlooped object for conditional displays .isEmpty() method available to invoke -->
				<c:choose>
					<c:when test="${!userThread.isEmpty()}">
						<c:forEach var="userThread" items="${userThread}">
							<!-- URL VARIABLE -->
							<c:set var="mainTopic_origin" value="${userThread.getForumSubTopic().getForumMainTopics().getTitle()}"/> 
							<c:set var="subTopic_origin" value="${userThread.getForumSubTopic().getTitle()}"/>
							
								 <li> <c:out value="[${mainTopic_origin} / ${subTopic_origin}]"/> </li>
								 <li> 
								 	<a href="/forums/${mainTopic_origin}/${subTopic_origin}/thread/${userThread.getId()}"> <c:out value="${userThread.getTitle()}"/> </a> 
								 </li>	 
						</c:forEach>
					</c:when>
					
					<c:otherwise>
						<li>No data found</li>
					</c:otherwise>
				</c:choose>
			</ul>
	</div>	 
	
	<div>	
		<h3> Activity Log </h3>
			<ul>
			<!-- unlooped object for conditional displays .isEmpty() method available to invoke -->
				<c:choose>
					<c:when test="${!userComments.isEmpty()}">
						<c:forEach var="userComments" items="${userComments}">
							<!-- URL VARIABLE -->
							<c:set var="mainTopic_origin" value="${userComments.getThreadTopic().getForumSubTopic().getForumMainTopics().getTitle()}"/> 
							<c:set var="subTopic_origin" value="${userComments.getThreadTopic().getForumSubTopic().getTitle()}"/>
							<c:set var="thread_origin_id" value="${userComments.getThreadTopic().getId()}"/>
							<c:set var="thread_origin_title" value="${userComments.getThreadTopic().getTitle()}"/>  
								 
								 <li> <c:out value="[${mainTopic_origin} / ${subTopic_origin}]"/> </li>
								 <li>
								 	<a href="/forums/${mainTopic_origin}/${subTopic_origin}/thread/${thread_origin_id}"> <c:out value="${thread_origin_title}"/> </a> 
								 </li>
								 
								 <li> <c:out value="${userComments.getComment()}"/> </li>
								 <hr>
						</c:forEach>
					</c:when>
					
					<c:otherwise>
						<li>No data found</li>
					</c:otherwise>
				</c:choose>
			</ul>
	</div>	 
	

	
</body>
</html>