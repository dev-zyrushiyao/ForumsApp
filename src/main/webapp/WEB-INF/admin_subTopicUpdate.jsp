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
<title>Update Sub Topic</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
</head>
<body>

<h1>Update Sub Topic ID: <c:out value="${updateSubTopicForm.getId()}"/></h1>
	<p><a href="/admin/view/${updateSubTopicForm.getForumMainTopics().getTitle()}/subtopic">GO BACK</a></p>
	<form:form action="/admin/update/info/sub/topic/id/${updateSubTopicForm.getId()}" method="POST" modelAttribute="updateSubTopicForm">
		<input type="hidden" name="_method" value="put">
		<label style="color:green"><c:out value="${updateTopicMessage}"/></label>
		<ul>
			<li>
				<label>Main Topic Title: </label>
				<form:input  path="title" type="text"/>
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
					<!-- To be Hidden -->
					<form:label path="forumMainTopics">Main Topic ID: </form:label>
					<form:input path="forumMainTopics" type="text" value="${MainTopicName.getId()}" readonly="true"/>
			</li>
		</ul>
		<input type="submit" value="UPDATE">
		
	</form:form>
	
		

</body>
</html>