<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>Asiakkaiden lisäys</title>

<link rel="stylesheet" href="css/tyyli.css" />
</head>

<%@ include file="valikko.jsp"%>


<body>
	<h3>Lisää asiakas</h3>
	<form action="ServletLisaaAsiakas" method="post">

		<table>
			<tr>
				<td>Nimi:</td>
				<td align="right"><input type="text" name="nimi"></td>
			</tr>
			<tr>
				<td>Osoite:</td>
				<td align="right"><input type="text" name="osoite"></td>
			</tr>
			<tr>
				<td>Puhelin:</td>
				<td align="right"><input type="text" name="puhelin"></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td align="right"><input type="text" name="email"></td>
			</tr>
			<tr>
				<td>Salasana:</td>
				<td align="right"><input type="password" name="salasana">
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="Lisää"></td>
				<td><input type="reset" value="Tyhjennä"></td>
			</tr>
		</table>
	</form>

</body>
</html>
