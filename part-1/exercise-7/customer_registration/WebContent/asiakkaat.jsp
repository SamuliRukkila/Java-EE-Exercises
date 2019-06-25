<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>Asiakkaat</title>
<link rel="stylesheet" href="css/tyyli.css" />
</head>


<%@ include file="valikko.jsp"%>
<body>

	<h3>Asiakkaat</h3>
	

	<%-- Muodostetaan lomake, jossa asiakkaiden tiedot näytetään.
             Lomake mahdollistaa yhden asiakkaan valitsemisen.
             Valitun asiakkaan tiedot lähetetään ServletAsiakasBean -servletille.
             joka luo Asiakasbeanin jossa ovat muokattavan asiakkaan tiedot --%>
	<form name="muokkaaAsiakas" method="post" action="ServletAsiakasBean">

		<table>

			<tr>
				<th>&nbsp;</th>
				<th>id</th>
				<th>Nimi</th>
				<th>Osoite</th>
				<th>Puhelin</th>
				<th>Email</th>
				<th>Salasana</th>
			</tr>
			<c:forEach items="${requestScope.list}" var="item">
				<tr>
					<td align="center"><input type="checkbox" name="id"
						value="${item.id}"></td>
					<td><c:out value="${item.id}" /></td>
					<td><c:out value="${item.nimi}" /></td>
					<td><c:out value="${item.osoite}" /></td>
					<td><c:out value="${item.puhelin}" /></td>
					<td><c:out value="${item.email}" /></td>
					<td><c:out value="${item.salasana}" /></td>
				</tr>
			</c:forEach>
			<tr>
				<td><input type="submit" name="submit" value="Muokkaa"></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>

			</tr>
		</table>
	</form>
</body>
</html>