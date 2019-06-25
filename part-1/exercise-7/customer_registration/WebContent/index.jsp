<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>index</title>
</head>
<body>

	<%
		/*Index-sivun olemassaolo estää käyttäjää näkemästä hakemistorakennetta
		jos se on palvelimella sallittu. Käyttäjä ohjataan suoraan login-sivulle.*/
		response.sendRedirect("login.html");
	%>

</body>
</html>
