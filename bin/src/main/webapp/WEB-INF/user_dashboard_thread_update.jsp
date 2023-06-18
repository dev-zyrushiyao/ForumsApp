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
<title>Dojo Dev Forum | New Thread</title>
</head>
<body>

<a href="/forums/${ForumMainTopic.getTitle()}/${forumSubTopic.getTitle()}/thread/${threadModel.getId()}"> GO BACK</a>
<br>
		<label>Update thread</label>
		<br>
 		<form:form action="/admin/forums/update/thread/info/id/${threadModel.getId()}" method="POST" modelAttribute="threadUpdateForm">
			<input type="hidden" name="_method" value="put">
			<ul>
				<li>
					<label>Topic Title: </label>
					<form:input path="title" type="text"/>
					<br>
					<form:errors path="title" class="text-danger" style="color:red"/>
				</li>
				<li>
					<label>Post content: </label>
					<form:textarea path="content" type="text"/>
					<br>
					<form:errors path="content" class="text-danger" style="color:red"/> 
				</li>
			
					<li> <!-- TO BE HIDDEN  -->
					<label>forumSubTopic ID: </label>
					<form:input path="forumSubTopic" type="text" value="${forumSubTopic.getId()}" readonly="true"/>
					<br>
					<form:errors path="forumSubTopic" class="text-danger" style="color:red"/>
				</li>	
				<li> <!-- TO BE HIDDEN  -->
					<label>User Thread ID: </label>
					<form:input path="userThread" type="text" value="${currentUser.getId()}" readonly="true"/>
					<br>
					<form:errors path="userThread" class="text-danger" style="color:red"/>
				</li>
				<li>
					<input type="submit" value="UPDATE TOPIC">
					<input type="reset" value="Clear">
				</li>
			</ul>
			

			</form:form>

</body>
</html>