<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<!-- Data Binding -->
		<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
			<!-- BindingResult -->
			<%@ page isErrorPage="true" %>

				<!DOCTYPE html>
				<html>

				<head>
					<meta charset="ISO-8859-1">
					<title><c:out value="${subTopic.getTitle()}" /> | Dojo Dev Forum</title>
					<!-- Bootstrap CSS -->
					<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />

					<link rel="icon" type="image/x-icon" href="/img/favicon.ico">
					<link rel="stylesheet" href="/css/style.css">
				</head>

				<body>

					<!-- Header when logged in -->
					<header class="main-header flex-row spc-bet">
						<div>
							<h1 class="main-header-title font-color-primary">&lt; Dojo Dev Forum &gt;</h1>
						</div>
						<!-- Profile Header Section -->
						<div class="flex-row flex-centered dropdown">
							<img id="profile-pic" src="/img/default-img.png" alt="Default profile picture">
							<p class="header-profile-name font-color-primary">
								<c:out value="${currentUser.getUserName()}" />&nbsp;&nbsp;<span
									class="caret-down">&#9660;</span>
							</p>

							<!-- Dropdown Content Section -->
							<div class="dropdown-content">

								<!-- ADMIN ACCESS ONLY -->
								<c:forEach var="currentUserRole" items="${currentUser.getRoles()}">
									<c:if test="${currentUserRole.getName().equals('ROLE_ADMIN')}">
										<form id="adminForm" method="GET" action="/admin">
											<a class="dropdown-menu-loc logout">
												<input id="adminDash-btn" type="submit" value="Admin Dashboard" />
											</a>
										</form>
									</c:if>
								</c:forEach>

								<!-- DROPDOWN MENU FOR ALL -->
								<a class="dropdown-menu-loc" href="/user/profile/${currentUser.getUserName()}/">View
									Profile</a>
								<a class="dropdown-menu-loc" href="/update/user/profile/id/${currentUser.getId()}">Edit
									Profile</a>
								<form id="logoutForm" method="POST" action="/logout">
									<a class="dropdown-menu-loc logout">
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
										<input id="logout-btn" type="submit" value="Logout!" />
									</a>
								</form>
							</div>


						</div>
					</header>


					<main class="main-content-logged">

						<div>
						<!-- BREADCRUMB -->
						<h5 class="margin-bot"><a href="/">Dashboard</a> > <a href="/forums/${mainTopic.getTitle()}">${mainTopic.getTitle()}</a> > ${subTopic.getTitle()}</h5>

						<div class="flex-row spc-bet">
							
							<div>
								<nav>
									<h1 class="margin-bot-smlr word-break">This is the ${subTopic.getTitle()} thread!</h1>
									<p>${subTopic.getDescription()}</p>
								</nav>

								<form action="/forums/${mainTopic.getTitle()}/${subTopic.getTitle()}/new/thread/"
									method="GET">
									<input class="margin-bot" type="submit" value="&#10010; New Thread">
								</form>


								<div id="main-forum-div">
									<div class="main-topic-div">
										<div class="main-header">
											<div class="main-title">
												<!-- <h4>Threads (${subTopic.getThreadTopics().size()}):</h4> -->
												<h4>Threads (${threadPages.getTotalElements()}):</h4>
												<p>Page <span id="thread-current-page">${threadPages.getNumber()+1}</span> of ${threadPages.getTotalPages()}</p>
												<c:forEach var="listOfThread" items="${listOfThread}">
													<ul>
														<li><a
																href="/forums/${mainTopic.getTitle()}/${subTopic.getTitle()}/thread/${listOfThread.getId()}">
																<c:out value="${listOfThread.getTitle()}" />
															</a></li>
														<li>Posted by: <a
																href="/user/profile/${listOfThread.getUserThread().getUserName()}">
																<c:out
																	value="${listOfThread.getUserThread().getUserName()}" />
															</a></li>
													</ul>
													<hr>
												</c:forEach>
											</div>
										</div>
									</div>
								</div>
								<c:if test="${subTopic.getThreadTopics().size()>0}">
								<nav aria-label="Page navigation">
									
									<ul class="pagination">

										<!-- BACK BUTTON -->

										<c:choose>

											<c:when test="${threadPages.getNumber() == 0}">
												<li class="page-item disabled">	
											</c:when>

											<c:otherwise>
												<li class="page-item">	
											</c:otherwise>

										</c:choose>
											
										<a class="page-link" href="/forums/${mainTopic.getTitle()}/${subTopic.getTitle()}/page/${threadPages.getNumber()-1}" aria-label="Previous">
												<span aria-hidden="true">&lt;&lt;</span>
											</a>
										</li>
										
										

										<!-- Pages Content -->
										<c:forEach begin="1" end="${totalPages}" step="1" varStatus="loop">
											<!-- loop how many pages -->
											<c:set var="pageCount" value="${loop.count-1}"></c:set>
											<!-- loop for pageTarget Route -->
											<li class="page-item"><a class="page-link"
													href="/forums/${mainTopic.getTitle()}/${subTopic.getTitle()}/page/${pageCount}">${pageCount+1}</a>
											</li>
										</c:forEach>

										<!-- NEXT BUTTON -->

										<c:choose>
											<c:when test="${threadPages.getNumber()+1 == threadPages.getTotalPages()}">
												<li class="page-item disabled">
											</c:when>
											
											<c:otherwise>
												<li class="page-item">
													
											</c:otherwise>
										</c:choose>

											<a class="page-link" href="/forums/${mainTopic.getTitle()}/${subTopic.getTitle()}/page/${threadPages.getNumber()+1}" aria-label="Next">
												<span aria-hidden="true">&gt;&gt;</span>
											</a>
										</li>

									</ul>
									
									<p>Page <span id="thread-current-page">${threadPages.getNumber()+1}</span> of ${threadPages.getTotalPages()}</p>
								</nav>
							</c:if>





							<!-- IF THERE IS NO THREAD YET -->
							<c:if test="${subTopic.getThreadTopics().size()==0}">
								<div class="flex-row flex-centered-2 margin-top">
									<h5>No discussions for this Topic yet</h5>
								</div>
							</c:if>

							</div>

						
					</main>



					<!-- Link JavaScript File -->
					<script src="/js/app.js"></script>
				</body>

				</html>