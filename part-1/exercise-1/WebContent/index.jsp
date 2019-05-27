<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Servlet Tutorial</title>
	</head>
	<body>
		<!-- Request scope -variable. Will only last through this site. After we go to home.jsp -file, it'll disappear -->
		Message from request: <b><%= request.getAttribute("mySecretMessage") %></b><br />
		<!-- Session scope -variable. Will last as long as user is in contact with the application -->
		Message from session: <b><%= session.getAttribute("mySecretMessage") %></b><br />
		<!-- Context/application scope -variable. Will last through the whole applicaton's life cycle -->
		Message from context: <b><%= request.getServletContext().getAttribute("mySecretMessage") %></b><br />
		<!-- Same attribute via Expression Language (doesn't need object to display) -->
		Message from EL: <b>${mySecretMessage}</b><br />
		
		<a href="home.jsp">Go Home!</a>
  </body>
</html>