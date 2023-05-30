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
<title>Update Main Topic</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
</head>
<body>

<h1>Update Main Topic ID: <c:out value="${updateMainTopicForm.getId()}"/></h1>
	<p><a href="/admin/view/main/topic">GO BACK</a></p>
	<form:form action="/admin/update/info/main/topic/id/${updateMainTopicForm.getId()}" method="POST" modelAttribute="updateMainTopicForm">
		<input type="hidden" name="_method" value="put">
		<label style="color:green"><c:out value="${updateTopic}"/></label>
		<ul>
			<li>
				<label>Main Topic Title: </label>
				<form:input  path="title" type="text"/>
				<form:errors path="title" class="text-danger" style="color:red"/> 
			</li>
			<li>
				<label>Description: </label>
				<form:input path="description" type="text"/>
				<form:errors path="description" class="text-danger" style="color:red"/> 
			</li>
		</ul>
		<input type="submit" value="UPDATE">
		
	</form:form>

</body>
</html>