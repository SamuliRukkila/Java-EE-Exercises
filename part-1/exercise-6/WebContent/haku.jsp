<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Haun tulokset</title>
		<link rel="stylesheet" href="css/tyyli.css">
	</head>
	<%@ include file="valikko.jsp"%>
	<body>
		<%
			String hakusana = (String) request.getAttribute("hakusana");
			String hakuvalinta = (String) request.getAttribute("hakuvalinta");
		%>
		
		<h3>Hakutulokset</h3>
		
		<p>Hakusana: <c:out value="${hakusana}"></c:out></p>
		<p>Valittu kenttä: <c:out value="${hakuvalinta}"></c:out></p>
		<p>Rivejä löydetty: <c:out value="${requestScope.list.size()}"></c:out></p>
		
		<c:if test="${requestScope.list.size() < 1}">
			<p><b>Hakutuloksilla ei löytynyt asiakkaita</b></p>
		</c:if>
		
		<c:if test="${requestScope.list.size() > 0}">
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
		</c:if>
	
	</body>
</html>