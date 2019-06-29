<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Palkkalomake</title>
	</head>
	<body>
		<%
			// Make sure the character encoding is UTF-8
			request.setCharacterEncoding("UTF-8");
			
			// Boolean value representing if convert was successful
			boolean success = false;
			
			// Get all user input's values and assign them to local variables
			String hoursReq = request.getParameter("hours");
			String salaryByHourReq = request.getParameter("salaryByHour");
			String taxReq = request.getParameter("tax");
			
			// Create needed variables where values will be put
			double hours = 0.0;
			double salaryByHour = 0.0;
			double tax = 0.0;
			
			// Try to convert all string-variables to decimal numbers
			try {
				hours = Double.parseDouble(hoursReq);
				salaryByHour = Double.parseDouble(salaryByHourReq);
				tax = Double.parseDouble(taxReq);
				success = true;
			} catch (NullPointerException npe) {
				out.println("Kaikkia lukuja ei voitu hakea. Jätitkö kohdan tyhjäksi?");
			} catch (Exception e) {
				out.println("Virhe palkkaa laskiessa");
			}
			
			// If convert was successful
			if (success) {
				double salary = salaryByHour * hours;
				out.println("<h3>Bruttopalkka:</h3>");
				out.println(salary + " €");
				out.println("<h3>Nettopalkka:</h3>");
				out.println(salary / (100 / (100 - tax)) + " €");
			}
		%>
			<p><a href="palkkalomake-A.html">Laske uudestaan</a></p>
		
	</body>
</html>