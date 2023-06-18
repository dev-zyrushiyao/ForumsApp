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
<title>Create Sub Topic</title>
</head>
<body>

<a href="/admin/view/${MainTopicName.getTitle()}/subtopic/"> GO BACK</a>
<br>
		<label>Create Sub Topic for <c:out value="${MainTopicName.getTitle()}"/></label>
		<br>

		<form:form action="/admin/create/${MainTopicName.getTitle()}/new/sub/topic" method="GET" modelAttribute="subTopicForm">
			
			<label style="color:green"><c:out value="${subTopicMessage}"></c:out></label>

		<ul>
				<li>
					<form:label path="title">Topic Title: </form:label>
					<form:input path="title" type="text"/>
					<br>
					<form:errors path="title" class="text-danger" style="color:red"/>
					
				</li>
				<li>
					<form:label path="description">Description: </form:label>
					<form:input path="description" type="text"/>
					<br>
					<form:errors path="description" class="text-danger" style="color:red"/> 
				</li>
				<li>
					<!-- To be Hidden -->
					<form:label path="forumMainTopics">Main Topic ID: </form:label>
					<form:input path="forumMainTopics" type="text" value="${MainTopicName.getId()}" readonly="true"/>
					
				<li>
					<input type="submit" value="CREATE TOPIC">
					<input type="reset" value="Clear">
				</li>
			</ul>

</form:form>

</body>
</html>