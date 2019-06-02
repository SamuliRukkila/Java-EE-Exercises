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
  	
  	<!-- Everything to UTF-8 -format -->
  	<% request.setCharacterEncoding("UTF-8"); %>
  	
  	<!-- Create (bean)object which'll work only in this page (scope="page") -->
    <jsp:useBean id="user" scope="page" class="beans.PersonBean"/>
    
    <!-- Every parameter we get to this page will be inserted to the created
    bean-object if there's setters for them. -->
    <jsp:setProperty name="user" property="*"/>
    
    <!-- Simple form which'll gather necessary information and set it to same 
    page -->
    <h3>Syötä tietosi</h3>
    <form action="UserBeanForm-A.jsp" method="post">
    	<p>Koko nimi:</p><input type="text" name="name">
    	<p>Osoite:</p><input type="text" name="address">
    	<p>Kotikunta:</p><input type="text" name="city">
    	<p>Sähköposti:</p><input type="text" name="email"> 
    	<p><input type="submit" value="Lähetä"></p>
    </form>
    
    <!-- After form is successfully sent, values will be distruped to these
    tags (according to their names) -->
    <h3>Syöttämäsi tiedot:</h3>
	  <p><jsp:getProperty name="user" property="name"/></p>
	  <p><jsp:getProperty name="user" property="address"/></p>
	  <p><jsp:getProperty name="user" property="city"/></p>
	  <p><jsp:getProperty name="user" property="email"/></p>
  </body>
</html>