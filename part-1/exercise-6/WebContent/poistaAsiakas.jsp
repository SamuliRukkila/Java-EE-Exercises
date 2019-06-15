<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Asiakkaan poisto</title>
<link rel="stylesheet" href="css/tyyli.css" />
</head>

<%@ include file="valikko.jsp"%>

<body>
	<h3>Poista asiakas</h3>

	<%-- 
        RequestScopesta saadaan map jossa ovat id:t ja nimet.
        Map käydään läpi c:forEach:lla. Nimet (item.value) näkyvät 
        listassa ja id (item.key) on option value joka välitetään 
        lomakkeelta poistoservletille jossa poisto toteutetaan id:n 
        perusteella.  
        --%>

	<form method="post" action="ServletPoistaAsiakas">

		<table>
			<tr>
				<td><select name="id">

					<c:forEach items="${requestScope.map}" var="item">

						<option value="${item.key}"><c:out value="${item.value}" /></option>

					</c:forEach>

				</select></td>
				<td><input type="submit" name="Submit" value="Poista">
				</td>
			</tr>
		</table>

	</form>

</body>
</html>
