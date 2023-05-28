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
<title> <c:out value="${userLogged.getUserName()} | Profile"></c:out></title>
</head>
<body>
	
	<a href="/dashboard">GO BACK</a>
	<!-- Profile Info -->
	<c:choose>
		<c:when test="${userModelDataChecker.getUserData() == null}">
			<ul>
				<li>Username: <c:out value="${userLogged.getUserName()}"/></li>
				<li>Joined at: <c:out value="${userLogged.getCreatedAt()}"/></li>
			</ul>
		</c:when>
		
		<c:otherwise>
			<ul>
				<!-- <li>USER IMAGE PLACE HOLDER</li> -->
				<li>Username: <c:out value="${userLogged.getUserName()}"/></li>
				<li>Name: <c:out value="${userLogged.getUserData().getFirstName()} ${userLogged.getUserData().getLastName()}"/></li>
				<li>Location: <c:out value="${userLogged.getUserData().getLocation()}"/></li>
				<li>Fav. Programming Language <c:out value="${userLogged.getUserData().getProgrammingLanguage()}"/></li>
				<li>Joined at: <c:out value="${userLogged.getCreatedAt()}"/></li>
				
			</ul>
		</c:otherwise> 
	</c:choose>
	
	<c:choose>
		<c:when test="${userModelDataChecker.getUserData() == null}">
			<form:form action="/post/userdata" method="POST" modelAttribute="userDataForm">
			<label style="color:green"><c:out value="${dataSuccess}"></c:out></label>
			<br>
			<label>Complete your profile</label>
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
					<label>Favorite Prog. Language</label>
					<form:input path="programmingLanguage" type="text"/>
					<form:errors path="programmingLanguage" class="text-danger" style="color:red"/>
				</li>
				<li>
					<label>User ID</label> <!-- to be hidden -->
					<form:input path="userAccount" type="text" value="${userLogged.getId()}" readonly="true"/> 
				</li>
				<li>
					<input type="submit" value="Submit">
					<input type="reset" value="Clear">
				</li>
			</ul>
			</form:form>
		</c:when>
		
		<c:otherwise>
			<form action="/update/user/id/${userLogged.getId()}" method="GET">
				<input type="submit" value="Update profile">
			</form>
		</c:otherwise>
	</c:choose>		
		
	
	
</body>
</html>