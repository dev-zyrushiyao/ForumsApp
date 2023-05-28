<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page</title>
</head>
<body>
		
		
	<a href="/registration">Register here</a>
		
    	
    <h1>Login</h1>
    	<c:if test="${logoutMessage != null}">
	        <label style="color:green"><c:out value="${logoutMessage}"></c:out></label>
	    </c:if>
    		
		<c:if test="${errorMessage != null}">
		     <label style="color:red"><c:out value="${errorMessage}"></c:out></label>
		</c:if>
		    
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
					<input type="submit" value="Login!"/>
			</form>
</body>
</html>