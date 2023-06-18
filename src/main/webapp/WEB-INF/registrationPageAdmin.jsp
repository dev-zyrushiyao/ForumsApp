<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Registration Page</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	
    <header class="main-header">
		<h1 class="main-header-title font-color-primary">Dojo Dev Forums</h1>
	</header>


    <main class="flex-column flex-centered flex-centered-2">

        <div class="login-container-admin">
            <div class="admin-icon"></div>
            <h1>Create admin account</h1>
            <hr>
            <p style="color:red"><form:errors path="user.*"/></p> 
            <p style="color:green"><c:out value="${registrationMessageSuccess}"/></p>
            <p style="color:red"><c:out value="${registrationMessageError}"/></p> 
            <form:form method="POST" action="/registration_admin" modelAttribute="user">
                <p>
                    <!-- <form:label path="userName">Admin Username:</form:label> -->
                    <form:input class="text-input input-text-pri blk-border margin-y-sm" path="userName" type="text" placeholder="Admin Username"/>
                </p>
                <p>
                    <!-- <form:label path="password">Password:</form:label> -->
                    <form:input class="text-input input-text-pri blk-border margin-y-sm" type="password" path="password" placeholder="Password"/>
                </p>
                <p>
                    <!-- <form:label path="passwordConfirmation">Password Confirmation:</form:label> -->
                    <form:input class="text-input input-text-pri blk-border margin-y-sm" type="password" path="passwordConfirmation" placeholder="Confirm Password"/>
                </p>
                <input class="btn-primary" type="submit" value="Register!"/>
            </form:form> 
        </div>

        <a href="/login">Go back</a>

    </main>

	
	
    
    
	
    

    <!-- Link JavaScript File -->
	<script src="/js/app.js"></script>
</body>
</html>