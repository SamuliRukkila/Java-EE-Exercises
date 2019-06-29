<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="papu" class="classes.CustomerBean" scope="session">
</jsp:useBean>
<html>
	<head>
		<title>Muokkaa tietoja</title>
	</head>
	<body>
	
		<%
		// We'll first check if there's any values inside our bean.
		// IF there is, all of the bean's values will be placed inside
		// local variables. These values will be displayed in form.
		if (papu.getId() != null) {
			String id = papu.getId();
			String nimi = papu.getNimi();
			String osoite = papu.getOsoite();
			String puhelin = papu.getPuhelin();
			String email = papu.getEmail();
			String salasana = papu.getSalasana();
		%>
			
			<!-- In this form you can modify the attributes of the user -->
			<h3>Muokkaa tietoja:</h3>
			<form action="ControllerServlet" method="post">
				<p>Id: <input type="text" name="id" value="<%= id %>" readonly="readonly"></p>
				<p>Nimi: <input type="text" name="nimi" value="<%= nimi %>"></p>
				<p>Osoite: <input type="text" name="osoite" value="<%= osoite %>"></p>
				<p>Puhelin: <input type="text" name="puhelin" value="<%= puhelin %>"></p>
				<p>Email: <input type="text" name="email" value="<%= email %>"></p>
				<p>Salasana: <input type="text" name="salasana" value="<%= salasana %>"></p>
				<input type="submit" name="updateValues" value="Päivitä"> 
			</form>
		
		<!-- If no values were found inside bean (via GET) -->
		<% } else { %>
			<p>Asiakasta ei löytynyt antamallasi sähköpostilla</p>
		<% } %>
		
		<p><a href="index.html">Palaa hakulomakkeeseen</a></p>
	</body>
</html>
