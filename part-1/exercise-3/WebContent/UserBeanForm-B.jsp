<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>KäyttäjäBean-lomake</title>
  </head>
  <body>
  	
  	<%
  		// Variables whose'll contain new values from form
  		String name = "";
  		String address = "";
  		String city = "";
  		String email = "";
  	%>
  	
  	<!-- Everything to UTF-8 -format -->
  	<% request.setCharacterEncoding("UTF-8"); %>
  	
  	<!-- Create (bean)object which'll work only in this page (scope="page") -->
    <jsp:useBean id="user" scope="page" class="beans.PersonBean"/>
    
    <!-- Every parameter we get to this page will be inserted to the created
    bean-object if there's setters for them. -->
    <jsp:setProperty name="user" property="*"/>
    
    <%
    	// After user sends data via form to the bean-object, those values will be
    	// fetched inside local variables from the bean
    	if (user != null) {
    		name = user.getName() != null ? user.getName() : "";
    		address = user.getAddress() != null ? user.getAddress() : "";
    		city = user.getCity() != null ? user.getCity() : "";
    		email = user.getEmail() != null ? user.getEmail() : "";
    	}
    %>
    
    
    <!-- Simple form which'll gather necessary information and set it to same 
    page -->
    <h3>Syötä tietosi</h3>
    <form action="UserBeanForm-B.jsp" method="post">
    	<p>Koko nimi:</p><input type="text" name="name" value="<%= name %>">
    	<p>Osoite:</p><input type="text" name="address" value="<%= address %>">
    	<p>Kotikunta:</p><input type="text" name="city" value="<%= city %>">
    	<p>Sähköposti:</p><input type="text" name="email" value="<%= email %>"> 
    	<p><input type="submit" value="Lähetä"></p>
    </form>
    
    
    <!-- After form is successfully sent, values will be distruped to these
    tags (according to their names) -->
    <h3>Syöttämäsi tiedot:</h3>
    <% if (user != null) { %>
	    <p><jsp:getProperty name="user" property="name"/></p>
	    <p><jsp:getProperty name="user" property="address"/></p>
	    <p><jsp:getProperty name="user" property="city"/></p>
	    <p><jsp:getProperty name="user" property="email"/></p>
    <% } %>
  </body>
</html>