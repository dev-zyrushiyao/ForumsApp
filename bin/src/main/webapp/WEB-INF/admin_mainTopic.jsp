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
<title>Create Main Topic</title>
</head>
<body>

<a href="/admin/view/main/topic"> GO BACK</a>
<br>
		<label>Create Main Topic</label>
		<br>
		<form:form action="/admin/create/new/main/topic" method="GET" modelAttribute="mainTopicForm">
			
			<label style="color:green"><c:out value="${mainTopicMessage}"></c:out></label>

		<ul>
				<li>
					<label>Topic Title: </label>
					<form:input path="title" type="text"/>
					<br>
					<form:errors path="title" class="text-danger" style="color:red"/>
					
				</li>
				<li>
					<label>Description: </label>
					<form:input path="description" type="text"/>
					<br>
					<form:errors path="description" class="text-danger" style="color:red"/> 
				</li>
				<li>
					<input type="submit" value="CREATE TOPIC">
					<input type="reset" value="Clear">
				</li>
			</ul>

</form:form>

</body>
</html>