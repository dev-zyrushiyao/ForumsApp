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
<title>Update Profile</title>
</head>
<body>
	
	<h1>Update Profile </h1>
	<a href="/dashboard">GO BACK</a> 
	
 	<label style="color:red"><c:out value="${userUpdateMessage}"/></label> 
 	<label style="color:green"><c:out value="${userUpdateSuccess}"/></label> 
	<form:form action="/update/user/info/${userInfo.getId()}" method="POST" modelAttribute="updateProfileForm">
				<input type="hidden" name="_method" value="put">
				<table class="table">
				  <thead>
				
				  </thead>
				  <tbody>
				    
				    <tr>
				      	<td><form:label path="firstName">First Name: </form:label></td>
				      	<td><form:input type="text" path="firstName"/></td>
				      	<td><form:errors path="firstName" class="text-danger" style="color:red"/>test</td>    
				    </tr>
				    
				      <tr>
				      	<td><form:label path="lastName">Last Name: </form:label></td>
				      	<td><form:input type="text" path="lastName"/></td>
				      	<td><form:errors path="lastName" class="text-danger" style="color:red"/>test</td>    
				    </tr>
				    
				    <tr>
				      	<td><form:label path="location">location: </form:label></td>
				      	<td><form:input type="email" path="location"/></td>
				      	<td><form:errors path="location" class="text-danger" style="color:red"/>test</td>    
				    </tr>
				    
				        
				     <tr>
				      	<td><form:label path="programmingLanguage">Email: </form:label></td>
				      	<td><form:input type="text" path="programmingLanguage"/></td>
				      	<td><form:errors path="programmingLanguage" class="text-danger" style="color:red"/></td>    
				    </tr> 
				    
				    <tr>
				      	<td><form:label path="userAccount">User Account ID: </form:label></td>
				      	<td><form:input type="text" path="userAccount"/></td>
				      	<td><form:errors path="userAccount" class="text-danger" style="color:red"/></td>    
				    </tr> 
					
				    
				  </tbody>
				</table>
				<input type="submit" value="Submit"> <input type="reset" value="Clear">
			</form:form>
</body>
</html>