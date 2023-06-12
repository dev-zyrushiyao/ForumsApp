<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Login Page</title>
	<link rel="stylesheet" href="css/style.css">
</head>
<body>
		
		
	
		
    	
    <h1>Dojo Dev Forums</h1>
    	<c:if test="${logoutMessage != null}">
	        <label style="color:green"><c:out value="${logoutMessage}"></c:out></label>
	    </c:if>
    		
		<c:if test="${errorMessage != null}">
		     <label style="color:red"><c:out value="${errorMessage}"></c:out></label>
		</c:if>


		<div class="login-container">
			<form method="POST" action="/login">
				<p>
					<label for="username">Username</label>
					<input type="text" id="username" name="username"/>
				</p>
				<p>
					<label for="password">Password</label>
					<input type="password" id="password" name="password"/>
				</p>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<input type="submit" value="Login"/>
			</form>
			
		</div>

		<span>Don't have an account?</span>
		<a class="btn" href="/registration">Join now!</a>


</body>
</html>