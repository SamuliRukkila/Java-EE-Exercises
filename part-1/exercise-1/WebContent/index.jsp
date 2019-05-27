<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Servlet Tutorial</title>
	</head>
	<body>    
		Message from request: <b><%= request.getAttribute("mySecretMessage") %></b><br />
		Message from session: <b><%= session.getAttribute("mySecretMessage") %></b><br />
		Message from context: <b><%= request.getServletContext().getAttribute("mySecretMessage") %></b><br />
		Message from EL: <b>${mySecretMessage}</b><br />
		
		<a href="home.jsp">Go Home!</a>
  </body>
</html>