<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--Sivujen yläosaan incluudattava valikko jossa tarkistetaan onko
loggauduttu sisään ja jossa voidaan loggautua ulos sovelluksesta. 
Valikossa on myös haku, sekä lisäyksen ja poiston linkki.--%>
<%
	/* Koska valikko on jokaisella jsp-sivulla, tapahtuu loggautumisen
	tarkistus jokaisella jsp-sivulla */
	String log = (String) session.getAttribute("login");
	try {
		if (log != null && log.equals("ok")) {
%>

<form name="kirjaudu_ulos" method="post" action="ServletLoginUlos">
	<a href='AsiakkaatServlet'>Asiakkaat</a> | 
	<a href='lisaaAsiakas.jsp'>Lisää asiakas</a> | 
	<a href='MapCreateServlet'>Poista asiakas</a> | 
	<input type="submit" name="Submit" value="Kirjaudu ulos">
</form>

<form name="hae_tietueita" method="post" action="ServletHaeTietoja">
	Hae tietueita: <input type="text" name="hakusana" /> <select
		name="hakuvalinta">
		<option value="nimi">Nimi</option>
		<option value="osoite">Osoite</option>
		<option value="puhelin">Puhelin</option>
		<option value="email">Email</option>
	</select> <input type="submit" name="Submit" value="Hae" />
</form>

<%
	} else {
			//ei-loggautuneet takaisin login-sivulle
			response.sendRedirect("login.html");
		}
	} catch (Exception e) {
		response.sendRedirect("login.html");
		return;
	}
%>

