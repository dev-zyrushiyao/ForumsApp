<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Title Page</title>
</head>
<body>

	
	
	<a href="/login">Go back</a>
	 <h1>Register! USER</h1>
    
    <p style="color:red"><form:errors path="user.*"/></p> 
    <p style="color:green"><c:out value="${registrationMessageSuccess}"/></p>
    <p style="color:red"><c:out value="${registrationMessageError}"/></p> 
    <form:form method="POST" action="/registration" modelAttribute="user">
        <p>
            <form:label path="userName">Username:</form:label>
            <form:input path="userName" type="text"/>
        </p>
        <p>
            <form:label path="password">Password:</form:label>
            <form:input type="password" path="password"/>
        </p>
        <p>
            <form:label path="passwordConfirmation">Password Confirmation:</form:label>
            <form:input type="password" path="passwordConfirmation"/>
        </p>
        <input type="submit" value="Next Step &#8811;"/>
    </form:form> 
	
</body>
</html>