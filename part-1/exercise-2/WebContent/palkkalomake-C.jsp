<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Laske palkkasi</title>
	</head>
	<body>
		<h3>Laske palkkasi</h3>
		
		<!-- Force inputs to number and allow them to include decimals.
    Send form-values to servlet. -->
		<form name='countPayment' method="post" action="LaskePalkkaServletC">
			<p>Tehdyt tunnit:</p> 
			<input type="number" step="any" name="hours" required>
			<p>Tuntipalkka</p>
			<input type="number" step="any" name="salaryByHour" required>
			<p>Veroprosentti:</p>
			<input type="number" step="any" name="tax" required>
			<p><input type="submit" value="Laske palkka"></p>
		</form>
		
		<%
			request.setCharacterEncoding("UTF-8");
			// If there were error while calculating payment
			if (request.getAttribute("error") != null) {
				out.println(request.getAttribute("error"));
			}	
			// If both salary and gross-salary were returned
			else if (request.getAttribute("salary") != null && request.getAttribute("grossSalary") != null) {		
				out.println("<h3>Bruttopalkka:</h3>");
				out.println(request.getAttribute("salary") + " €");
				out.println("<h3>Nettopalkka:</h3>");
				out.println(request.getAttribute("grossSalary") + " €");
			} else {
				out.println("Palkkasi tulee tähän näkyviin");
			}
		%>
	</body>
</html>