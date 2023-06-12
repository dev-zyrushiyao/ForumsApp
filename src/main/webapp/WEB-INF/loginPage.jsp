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
	
	<header class="main-header">
		<h1 class="main-header-title font-color-primary">Dojo Dev Forums</h1>
	</header>
    
		<div class="">
			<h2 class="font-color-primary">Connect with a dojo full of developers around the globe</h2>
		</div>

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
					<input class="btn-secondary margin-bot" type="submit" value="Login"/>
			</form>

			<div>
				<c:if test="${errorMessage != null}">
					 <label style="color:red"><c:out value="${errorMessage}"></c:out></label>
				</c:if>

			</div>

			<div class="margin-y">
				<span>Don't have an account?</span>
				<a class="btn-primary blk-border" href="/registration">Join now!</a>
			</div>
			

			<c:if test="${logoutMessage != null}">
				<label style="color:green"><c:out value="${logoutMessage}"></c:out></label>
			</c:if>
		</div>



</body>
</html>