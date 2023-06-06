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
	
	<a href="/login"> Complete profile later</a>
		<c:if test="${userModelDataChecker.getUserData() == null}"> 
			<form:form action="/registration/post/userdata/${currentUser.getUserName()}" method="POST" modelAttribute="userDataForm">
			<label style="color:green"><c:out value="${userDataMessage}"></c:out></label>
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
					<form:input path="userAccount" type="text" value="${currentUser.getId()}" readonly="true"/> 
				</li>
				<li>
					<input type="submit" value="Submit">
					<input type="reset" value="Clear">
				</li>
			</ul>
			</form:form>
		 </c:if> 
		
		 
	
	
</body>
</html>