<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Dojo Dev Forum - Login or Sign Up</title>
	<link rel="icon" type="image/x-icon" href="/img/favicon.ico">
	<link rel="stylesheet" href="/css/style.css">
</head>
<body>
	
	<header class="main-header">
		<h1 class="main-header-title font-color-primary">&lt; Dojo Dev Forum &gt;</h1>
	</header>
    
	<main>

		<div class="main-content flex-row flex-centered margin-bot-lgr">

		
			<div class="left-content flex-column">
				<h2 class="font-color-primary intro-text">Connect with a dojo full of developers around the globe</h2>
				<img src="/img/earth.png" alt="earth image" id="globe-img">
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
						<input id="log-btn" class="btn-secondary margin-y" type="submit" value="Login"/>
				</form>

				<div>
					<c:if test="${errorMessage != null}">
						<label style="color:red"><c:out value="${errorMessage}"></c:out></label>
					</c:if>

				</div>

				<div class="login-cont-message margin-y">
					<span>Don't have an account?</span>
					<a id="reg-btn" class="btn-primary blk-border" href="/registration">Join as user</a>
				</div>

				
				

				<c:if test="${logoutMessage != null}">
					<label style="color:green"><c:out value="${logoutMessage}"></c:out></label>
				</c:if>

				<div>
					<a href="/registration_admin">Become an admin</a>
				</div>

				<p style="color:green"><c:out value="${adminRegistrationMessageSuccess}"/></p>
			</div>
		
		</div>

		<div class="flex-row flex-centered flex-centered-2" id="rand-quote-display">
			<div>
				<h2 class="margin-bot font-color-primary" id="text-quote"></h2>
				<p class="font-color-primary" id="auth-quote"></p>
			</div>
		</div>
		
	</main>


	<!-- Link JavaScript File -->
	<script src="/js/app.js"></script>
</body>
</html>