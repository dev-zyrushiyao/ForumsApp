<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix= "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Data Binding -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- BindingResult -->
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title> <c:out value="${subTopic.getTitle()}"/> | Dojo Dev Forums</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
</head>
<body>
	
	<nav>
		<h1> Hello, <a href="/user/profile/${currentUser.getUserName()}/"><c:out value="${currentUser.getUserName()}"/></a></h1>
		<form id="logoutForm" method="POST" action="/logout">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <input type="submit" value="Logout!" />
    	</form>
    </nav>
    
    <form action="/forums/${mainTopic.getTitle()}/${subTopic.getTitle()}/new/thread/" method="GET">
    	<input type="submit" value="New Thread">
    </form>
    
	
 	<div id="main-forum-div">
		<div class="main-topic-div">
			<div class="main-header">
				<div class="main-title">
					<c:forEach var="listOfThread" items="${listOfThread}">
					<ul>	
						<li><a href="/forums/${mainTopic.getTitle()}/${subTopic.getTitle()}/thread/${listOfThread.getId()}"><c:out value="${listOfThread.getTitle()}"/></a></li>
						<li>- <a href="/user/profile/${listOfThread.getUserThread().getUserName()}"><c:out value="${listOfThread.getUserThread().getUserName()}"/></a></li>
					</ul>
					</c:forEach>
				</div>
			</div>
		</div>
	</div> 
	
	<nav aria-label="Page navigation">
	  <ul class="pagination">
	    <li class="page-item">
	      <a class="page-link" href="/forums/${mainTopic.getTitle()}/${subTopic.getTitle()}/page/0" aria-label="Previous">
	        <span aria-hidden="true">«</span>
	      </a>
	    </li>
	    
	    <!------------------------------------------------------------------->
			    <!--Example: Total Pages (2) - Loops start from 1 ~ 2 -->
			      <!--Example: PageCount - start from 0 -->
			      	<!--Last Page: 2-1 = 1; -->
			      	<!--First Page: 1-1 = 0; -->
	    <!------------------------------------------------------------------->
	    
	    <!-- Pages Content -->
		 <c:forEach begin="1" end="${totalPages}" step="1" varStatus="loop">  <!-- loop how many pages -->
		 	<c:set var="pageCount" value="${loop.count-1}"></c:set> <!-- loop for pageTarget Route -->
		    <li class="page-item"><a class="page-link" href="/forums/${mainTopic.getTitle()}/${subTopic.getTitle()}/page/${pageCount}">${pageCount}</a></li>
		</c:forEach>
	
	    <li class="page-item">
	      <a class="page-link" href="/forums/${mainTopic.getTitle()}/${subTopic.getTitle()}/page/${totalPages-1}" aria-label="Next">
	        <span aria-hidden="true">»</span>
	      </a>
	    </li>
	  </ul>
	</nav> 

</body>
</html>