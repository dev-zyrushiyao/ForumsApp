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
<title>View All Main Topics</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
</head>
<body>
<a href="/admin"> GO BACK</a>
<a  href="/admin/create/main/topic"> Add Main Topic </a>
<h1>All main topics</h1>
	
	
	<table class="table table-hover">
  <thead>
    <tr>
      <th scope="ID">#</th>
      <th scope="col">Title</th>
      <th scope="col">Description</th>
      <th scope="col">Action</th>
    </tr>
  </thead>
  <tbody>
  	
  	<c:forEach var="topicList" items="${forumMainTopic}">
	<tr>
	      <th scope="row"><c:out value="${topicList.getId()}"/></th>
	      <td><a href="/admin/view/${topicList.getTitle()}/subtopic/" title="View Sub-Topics for ${topicList.getTitle()}"> <c:out value="${topicList.getTitle()}"/> </a> </td>
	      <td> <c:out value="${topicList.getDescription()}"/> </td>
		      <td> 
		      	<div>
			      	<form action="/admin/update/main/topic/id/${topicList.getId()}" method="GET">
			      		<input type="submit" class="btn btn-primary" value="EDIT">
			      	</form>
			      				
					<form:form action="/admin/delete/main/topic/id/${topicList.getId()}" method="POST">
						 <input type="hidden" name="_method" value="DELETE"> 
						 <input type="submit" class="btn btn-danger" value="DELETE" onClick="return confirm('Are you sure you want to delete [ID:' + ${topicList.getId()} + ']')">
					</form:form>
			      	
		      	</div>
		      </td>
	</tr>
	</c:forEach> 	
  
  </tbody>
</table>
</body>
</html>