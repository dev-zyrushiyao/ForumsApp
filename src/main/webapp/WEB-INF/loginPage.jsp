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
    
	<main class="main-content flex-row flex-centered">

			<div class="left-content flex-column">
				<h2 class="font-color-primary intro-text">Connect with a dojo full of developers around the globe</h2>
				<img src="https://www.pngkit.com/png/full/781-7817356_international-shipping-icon-01-icon-globe-vector.png" alt="earth image" id="globe-img">
			</div>



			<div class="login-container">
				<form method="POST" action="/login">
					<div>
						<p class="input-label"><label for="username">Username:</label></p>
						<input class="text-input input-text-pri blk-border" type="text" id="username" name="username" placeholder="Your Username"/>
					</div>
					<div>
						<p class="input-label"><label for="password">Password:</label></p>
						<input class="text-input input-text-pri blk-border" type="password" id="password" name="password" placeholder="Your Password"/>
					</div>
					
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<input class="btn-secondary margin-y" type="submit" value="Login"/>
				</form>

				<div>
					<c:if test="${errorMessage != null}">
						<label style="color:red"><c:out value="${errorMessage}"></c:out></label>
					</c:if>

				</div>

				<div class="login-cont-message margin-y">
					<span>Don't have an account?</span>
					<a class="btn-primary blk-border" href="/registration">Join now!</a>
				</div>
				

				<c:if test="${logoutMessage != null}">
					<label style="color:green"><c:out value="${logoutMessage}"></c:out></label>
				</c:if>

			</div>
		
	</main>



</body>
</html>