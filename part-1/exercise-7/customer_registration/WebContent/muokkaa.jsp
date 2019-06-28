<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
	<head>
		<title>Muokkaa asiakasta</title>
		<link rel="stylesheet" href="css/tyyli.css">
	</head>
	<%@ include file="valikko.jsp"%>
	<body>
		<h3>Muokkaa asiakasta</h3>
		<c:if test="${requestScope.asiakas.id != null}">
			<form name="muokkaaAsiakas" method="post" action="ServletMuokkaaAsiakas">
				<table>
					<tr>
						<td>Id</td>
						<td align="right"><input type="text" name="id" readonly="readonly"
							value="<c:out value="${requestScope.asiakas.id}"/>"></td>
					</tr>
					<tr>
						<td>Nimi:</td>
						<td align="right"><input type="text" name="nimi"
							value="<c:out value="${requestScope.asiakas.nimi}"/>"></td>
					</tr>
					<tr>
						<td>Osoite:</td>
						<td align="right"><input type="text" name="osoite"
							value="<c:out value="${requestScope.asiakas.osoite}"/>"></td>
					</tr>
					<tr>
						<td>Puhelin:</td>
						<td align="right"><input type="text" name="puhelin"
							value="<c:out value="${requestScope.asiakas.puhelin}"/>"></td>
					</tr>
					<tr>
						<td>Email:</td>
						<td align="right"><input type="text" name="email"
							value="<c:out value="${requestScope.asiakas.email}"/>"></td>
					</tr>
					<tr>
						<td>Salasana:</td>
						<td align="right"><input type="password" name="salasana"
							value="<c:out value="${requestScope.asiakas.salasana}"/>">
						</td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Päivitä"></td>
					</tr>
				</table>
			</form>
		</c:if>
	</body>
</html>