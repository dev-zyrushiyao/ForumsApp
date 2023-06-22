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
	<title>Complete your profile</title>
	<link rel="stylesheet" href="../../../css/style.css">
</head>
<body>

	<header class="main-header">
		<h1 class="main-header-title font-color-primary">&lt; Dojo Dev Forum &gt;</h1>
	</header>
	
	

	<main class="flex-column flex-centered flex-centered-2">
		<div class="login-container ">
			<c:if test="${userModelDataChecker.getUserData() == null}"> 
				<form:form action="/registration/post/userdata/${currentUser.getUserName()}" method="POST" modelAttribute="userDataForm">
				<label style="color:green"><c:out value="${userDataMessage}"></c:out></label>
				
				<h1>Complete your profile</h1>
				<hr>
				
					
						<!-- <label>First Name: </label> -->
						<form:input class="text-input input-text-pri blk-border margin-y-sm" path="firstName" type="text" placeholder="First Name"/>
						<form:errors path="firstName" class="text-danger" style="color:red" />
						
						<!-- <label>Last Name: </label> -->
						<form:input class="text-input input-text-pri blk-border margin-y-sm" path="lastName" type="text" placeholder="Last Name"/>
						<form:errors path="lastName" class="text-danger" style="color:red"/> 
					
						<!-- <label>Location: </label> -->
						<form:input class="text-input input-text-pri blk-border margin-y-sm" path="location" type="text" placeholder="Your Location"/>
						<form:errors path="location" class="text-danger" style="color:red"/>
					
						<!-- <label>Favorite Prog. Language</label> -->
						<form:input class="text-input input-text-pri blk-border margin-y-sm" path="programmingLanguage" type="text" placeholder="Your Programming Language"/>
						<form:errors path="programmingLanguage" class="text-danger" style="color:red"/>
					
	
					<!-- User ID will be hidden -->
	
					<!-- <li>
						<label>User ID</label> 
					</li> -->
					
					<form:input path="userAccount" type="text" value="${currentUser.getId()}" hidden="true"/> 
					
					
						<input class="btn-primary blk-border" type="submit" value="Submit">
						<input class="btn-primary margin-y" id="clr-btn" type="reset" value="Clear">
					
				
				</form:form>
			 </c:if> 

			 <a href="/login">Complete profile later</a>
		</div>
	</main>
		
		 
	
	<!-- Link JavaScript File -->
	<script src="../../../js/app.js"></script>
</body>
</html>