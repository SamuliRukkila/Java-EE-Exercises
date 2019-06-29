<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Homepage</title>
	</head>
	<body>
		<!-- Will be null in this page because requests disappear after changing site -->
		Message from request: <b><%= request.getAttribute("mySecretMessage") %></b><br />
		<!-- Session scope -variable. Will last as long as user is in contact with the application --> 
		Message from session: <b><%= session.getAttribute("mySecretMessage") %></b><br /> 
		<!-- Context/application scope -variable. Will last through the whole applicaton's life cycle -->
		Message from context: <b><%= request.getServletContext().getAttribute("mySecretMessage") %></b><br /> 
		<!-- Instead of showing request's attribute here, it'll show session's message instead 
		because request is NULL -->
		Message from EL: <b>${mySecretMessage}</b>
</body>
</html>