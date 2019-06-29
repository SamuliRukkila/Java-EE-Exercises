<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="papu" class="classes.CustomerBean" scope="session"></jsp:useBean>
<html>
	<head>
		<title>vastaus.jsp</title>
	</head>
	<body>
		<% if (papu.getId() != null) { %>
			<p>Vastaus hakuun:</p>
			Id:
			<jsp:getProperty name="papu" property="id" />
			<br /> Nimi:
			<jsp:getProperty name="papu" property="nimi" />
			<br /> Osoite:
			<jsp:getProperty name="papu" property="osoite" />
			<br /> Puhelin:
			<jsp:getProperty name="papu" property="puhelin" />
			<br /> Email:
			<jsp:getProperty name="papu" property="email" />
			<br /> Salasana:
			<jsp:getProperty name="papu" property="salasana" />
			<br />
		
		<% } else { %>
			<p>Asiakasta ei löytynyt sähköpostin perusteella</p>
		<% } %>
		
		<p><a href="index.html">Palaa hakulomakkeeseen</a></p>
	</body>
</html>
